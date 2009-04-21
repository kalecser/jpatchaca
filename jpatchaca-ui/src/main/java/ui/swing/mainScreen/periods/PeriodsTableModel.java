/**
 * 
 */
package ui.swing.mainScreen.periods;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import lang.Maybe;

import org.apache.commons.lang.time.DateUtils;

import periods.Period;
import periods.PeriodsListener;
import periodsInTasks.PeriodsInTasksSystem;
import tasks.TasksSystem;
import tasks.tasks.TaskView;
import basic.Formatter;
import basic.Subscriber;

public class PeriodsTableModel extends AbstractTableModel {
	

	private static final long serialVersionUID = 1L;

	private static final String[] columnNames = new String[] { "Day", "Start", "End",
			"Time" };
	private static int[] editableColumns = new int[] { 0, 1, 2 };

	private final TasksSystem tasksSystem;
	private final PeriodsInTasksSystem periodsSystem;
	private PeriodsListener periodsListener;
	private final Formatter formatter;
	
	private TaskView _task = null;
	
	public PeriodsTableModel(TasksSystem tasksSystem, PeriodsInTasksSystem periodsSystem, Formatter formatter) {
		this.tasksSystem = tasksSystem;
		this.periodsSystem = periodsSystem;
		this.formatter = formatter;
		
		
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
		return PeriodsTableModel.columnNames.length;
	}

	public synchronized Object getValueAt(int rowIndex, int columnIndex) {
		
		if (this._task == null)
			return "";

		final Period period = periodForRowIndex(rowIndex);


		if (columnIndex == 0)
			return formatter.formatShortDateWithWeekday(period.startTime());
		if (columnIndex == 1)
			return formatter.formatShortTime(period.startTime());
		if (columnIndex == 2)
			return formatEndTime(period);
		if (columnIndex == 3)
			return formatter.formatTimeSpent(period.getMiliseconds()
					/ DateUtils.MILLIS_PER_HOUR);

		return "";
	}

	private String formatEndTime(final Period period) {
		if (period.endTime() != null) {
			return formatter.formatShortTime(period.endTime());
		} else {
			return "";
		}
	}

	private Period periodForRowIndex(int rowIndex) {

		return _task.periodAt(rowToIndex(rowIndex));
	}

	private int rowToIndex(int row) {
		int periodsCount = _task.periodsCount();
		if (periodsCount == 0)
			throw new RuntimeException("No periods in task");
		return periodsCount - row - 1;
	}
	
	@Override
	public String getColumnName(int column) {
		return PeriodsTableModel.columnNames[column];
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return Arrays.binarySearch(editableColumns, column) != -1;
	}

	@Override
	public void setValueAt(final Object value, final int row, final int column) {
		
		if (row == -1)
 			throw new RuntimeException("invalid row -1");
		
		final int rowToIndex = rowToIndex(row);
		enqueueSetValueAt(value, row, column, rowToIndex);
					
	}

	private void enqueueSetValueAt(final Object value, final int row,
			final int column, final int rowToIndex) {
		if (rowToIndex == -1)
			JOptionPane.showMessageDialog(null, "No row to edit");
			
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
			return Maybe.wrap(formatter.parseShortDate(dateString));
		} catch (ParseException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Invalid date");
			return null;
		}
	}

	private String removeWeekDay(String dateString) {
		dateString = dateString.replaceFirst("[A-Za-z]{3}", "");
		return dateString;
	}

	private Maybe<Date> parseEditTime(final Object value, final int row) {
		try {
			String insertedDateString = formatter.formatShortDate(periodForRowIndex(row).startTime())
					+ " " + (String) value;
			
			return Maybe.wrap((Date) formatter.parseShortDateTime(insertedDateString));
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

			public void periodAdded(final Period period) {
				SwingUtilities.invokeLater(new Runnable() {				
					@Override
					public void run() {
						add(period);
					}
				});
			}

			public void periodRemoved(final Period period) {
				SwingUtilities.invokeLater(new Runnable() {				
					@Override
					public void run() {
						remove(period);
					}
				});

			}
			

			private synchronized void remove(final Period period) {
				SwingUtilities.invokeLater(new Runnable() {				
					@Override
					public void run() {
						fireTableDataChanged();
						removeSubscriber(period);
					}
				});	
								
			}

			private synchronized void removeSubscriber(Period period) {
				Subscriber subscriber = subscribers.remove(period);
				if (subscriber != null)
					period.unsubscribe(subscriber);
			}

			private synchronized void add(final Period period) {
				
				final int affectedRow = rowToIndex(_task.periods().size() - 1);
				final Subscriber subscriber = new Subscriber() {
					@Override
					public void fire() {
					
								SwingUtilities.invokeLater(new Runnable() {
								
									@Override
									public void run() {
										
										fireTableRowsUpdated(affectedRow, affectedRow);
									}
								});
								
						
					}
				};
				
				period.subscribe(subscriber);
				fireTableRowsInserted(affectedRow, affectedRow);
				
	
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