/**
 * 
 */
package ui.swing.mainScreen.periods;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.reactivebricks.commons.lang.Maybe;

import periods.Period;
import periods.PeriodsListener;
import periodsInTasks.PeriodsInTasksSystem;
import tasks.TasksSystem;
import tasks.tasks.TaskView;
import basic.Subscriber;

public class PeriodsTableModel extends AbstractTableModel {
	
	private static final DecimalFormat TIME_SPENT_FORMAT = new DecimalFormat("#0.00");
	private static final FastDateFormat SHORT_DATE_FORMAT = FastDateFormat
			.getDateInstance(FastDateFormat.SHORT);

	private static final long serialVersionUID = 1L;

	private final TasksSystem tasksSystem;
	private final PeriodsInTasksSystem periodsSystem;


	private final String[] columnNames = new String[] { "Day", "Start", "End",
			"Time" };
	private int[] editableColumns = new int[] { 0, 1, 2 };
	private TaskView _task = null;

	String shortDatePattern = SHORT_DATE_FORMAT.getPattern();
	String weekDayPattern = "E";
	private final FastDateFormat dateFormat = FastDateFormat
			.getInstance(weekDayPattern + " " + shortDatePattern);
	private final FastDateFormat timeFormat = FastDateFormat
			.getTimeInstance(FastDateFormat.SHORT);

	private PeriodsListener periodsListener;



	public PeriodsTableModel(TasksSystem tasksSystem, PeriodsInTasksSystem periodsSystem) {
		this.tasksSystem = tasksSystem;
		this.periodsSystem = periodsSystem;
		
		
	}

	public int getRowCount() {
		if (this._task == null)
			return 0;
		return this._task.periods().size();
	}

	public Period getPeriodAt(int rowIndex) {
		return periodForRowIndex(rowIndex);
	}

	public int getColumnCount() {
		return this.columnNames.length;
	}

	public synchronized Object getValueAt(int rowIndex, int columnIndex) {
		if (this._task == null)
			return "";

		final Period period = periodForRowIndex(rowIndex);
		String endTime;
		if (period.endTime() != null) {
			endTime = timeFormat.format(period.endTime());
		} else {
			endTime = "";
		}

		if (columnIndex == 0)
			return dateFormat.format(period.startTime());
		if (columnIndex == 1)
			return timeFormat.format(period.startTime());
		if (columnIndex == 2)
			return endTime;
		if (columnIndex == 3)
			return TIME_SPENT_FORMAT.format(period.getMiliseconds()
					/ DateUtils.MILLIS_PER_HOUR);

		return "";
	}

	private Period periodForRowIndex(int rowIndex) {

		return _task.periodAt(rowToIndex(rowIndex));
	}

	private int rowToIndex(int row) {
		return _task.periodsCount() - row - 1;
	}
	
	@Override
	public String getColumnName(int column) {
		return this.columnNames[column];
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return Arrays.binarySearch(editableColumns, column) != -1;
	}

	@Override
	public void setValueAt(final Object value, final int row, final int column) {

		
		final int rowToIndex = rowToIndex(row);
		enqueueSetValueAt(value, row, column, rowToIndex);
					
	}

	private void enqueueSetValueAt(final Object value, final int row,
			final int column, final int rowToIndex) {
		new Thread(){
			@Override
			public void run() {
				if (column == 0){	
					Maybe<Date> parseDate = parseDate(value);
					if (parseDate != null)
						periodsSystem.editPeriodDay(_task, rowToIndex, parseDate.unbox());
				}
				
				if (column == 1){
					
					Maybe<Date> parseEditTime = parseEditTime(value, row);
					if (parseEditTime != null)
						tasksSystem.setPeriodStarting(_task, rowToIndex, parseEditTime.unbox());			
				}

				if (column == 2){
					Maybe<Date> parseEditTime = parseEditTime(value, row);
					if (parseEditTime != null)
						tasksSystem.setPeriodEnding(_task, rowToIndex, parseEditTime.unbox());
				}	
			}
		}.start();
	}

	private Maybe<Date> parseDate(Object value) {
		
		final String dateString = removeWeekDay(value.toString());
		
		try {
			return Maybe.wrap(new SimpleDateFormat(shortDatePattern).parse(dateString));
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Invalid date " + value);
			return null;
		}
	}

	private String removeWeekDay(String dateString) {
		dateString = dateString.replaceFirst("[A-Za-z]{3}", "");
		return dateString;
	}

	private Maybe<Date> parseEditTime(final Object value, final int row) {
		try {
			String insertedDateString = SHORT_DATE_FORMAT.format(periodForRowIndex(row).startTime())
					+ " " + (String) value;
			return Maybe.wrap((Date) new SimpleDateFormat(shortDatePattern + " "
					+ timeFormat.getPattern()).parseObject(insertedDateString));
		} catch (final ParseException e) {
			JOptionPane.showMessageDialog(null, "Invalid time " + value);
			return null;
		}
	}

	public final void setTask(TaskView task) {

		if (this._task != null)
			this._task.removePeriodListener(periodsListener);

		this._task = task;
		fireTableDataChanged();

		if (task == null)
			return;

		if (periodsListener != null)
			periodsListener.clean();
		
		periodsListener = createPeriodsListener();
		task.addPeriodsListener(periodsListener);
	}

	private PeriodsListener createPeriodsListener() {
		return new PeriodsListener() {
			private Map<Period, Subscriber> subscribers = new HashMap<Period, Subscriber>();

			public void periodAdded(Period period) {
				add(period);
			}

			public void periodRemoved(Period period) {
				remove(period);
			}
			

			private synchronized void remove(Period period) {
				fireTableDataChanged();
				removeSubscriber(period);
								
			}

			private synchronized void removeSubscriber(Period period) {
				Subscriber subscriber = subscribers.remove(period);
				if (subscriber != null)
					period.unsubscribe(subscriber);
			}

			private synchronized void add(final Period period) {
				final int affectedRow = rowToIndex(_task.periods().size() - 1);
				fireTableRowsInserted(affectedRow, affectedRow);

				Subscriber subscriber = new Subscriber() {
					@Override
					public void fire() {
						fireTableRowsUpdated(affectedRow, affectedRow);				
					}
				};
				
				period.subscribe(subscriber);
				
				subscribers.put(period, subscriber);
			
			}

			@Override
			public synchronized void clean() {
				for (Period period : new HashSet<Period>(subscribers.keySet())){
					removeSubscriber(period);
				}
				
			}
		};
	}

	
}