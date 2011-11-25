package ui.swing.mainScreen.periods;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import lang.Maybe;

import org.apache.commons.lang.time.DateUtils;
import org.reactive.Receiver;

import periods.Period;
import periodsInTasks.PeriodsInTasksSystem;
import tasks.TaskView;
import tasks.TasksSystem;
import ui.swing.tasks.SelectedTaskPeriods;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.utils.UIEventsExecutor;
import basic.Formatter;
import basic.Subscriber;

public class PeriodsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final String[] columnNames = new String[] { "Day", "Start",
			"End", "Time" };
	private static int[] editableColumns = new int[] { 0, 1, 2 };

	private final TasksSystem tasksSystem;
	private final PeriodsInTasksSystem periodsSystem;
	private final SelectedTaskPeriods selectedTaskperiods;
	private final Formatter formatter;
	private final PeriodsTableWhiteboard whiteborad;

	private TaskView _task = null;

	private Map<Integer, Subscriber> periodSubscriberByRow = new LinkedHashMap<Integer, Subscriber>();

	private final AtomicInteger size = new AtomicInteger(0);

	private final UIEventsExecutor executor;

	public PeriodsTableModel(final TasksSystem tasksSystem,
			final PeriodsInTasksSystem periodsSystem,
			final Formatter formatter, final SelectedTaskSource selectedTask,
			final SelectedTaskPeriods selectedTaskperiods,
			final PeriodsTableWhiteboard whiteborad,
			final UIEventsExecutor executor) {
		this.tasksSystem = tasksSystem;
		this.periodsSystem = periodsSystem;
		this.formatter = formatter;
		this.selectedTaskperiods = selectedTaskperiods;
		this.whiteborad = whiteborad;
		this.executor = executor;

		bindToSelectedTask(selectedTask);
		updateTableSizehenPeriodsChange(selectedTaskperiods);

	}

	@Override
	public int getRowCount() {
		return this.selectedTaskperiods.currentSize();
	}

	@Override
	public int getColumnCount() {
		return PeriodsTableModel.columnNames.length;
	}

	@Override
	public synchronized Object getValueAt(final int rowIndex,
			final int columnIndex) {
	
		if (rowIndex > getRowCount()) {
			return null;
		}
	
		final Period period = selectedTaskperiods.currentGet(rowIndex);
		subscribeToPeriod(rowIndex, period);
	
		if (columnIndex == 0) {
			return new ShortDateWithWeekDay(formatter, period.startTime());
		}
		if (columnIndex == 1) {
			return formatter.formatShortTime(period.startTime());
		}
		if (columnIndex == 2) {
			return formatEndTime(period);
		}
		if (columnIndex == 3) {
			return formatter.formatTimeSpent(period.getMiliseconds()
					/ DateUtils.MILLIS_PER_HOUR);
		}
	
		return "";
	}

	@Override
	public String getColumnName(final int column) {
		return PeriodsTableModel.columnNames[column];
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return Arrays.binarySearch(editableColumns, column) != -1;
	}

	@Override
	public void setValueAt(final Object value, final int row, final int column) {
	
		if (row == -1) {
			throw new RuntimeException("invalid row -1");
		}
	
		enqueueSetValueAt(value, row, column);
	
	}

	public Period getPeriod(final int selectedRow) {
		return selectedTaskperiods.currentGet(selectedRow);
	}

	private void updateTableSizehenPeriodsChange(
			final SelectedTaskPeriods periods) {
		periods.size().addReceiver(new Receiver<Integer>() {
			@Override
			public void receive(final Integer value) {
				changeTableSize(value);
			}
		});
	}

	private void updateTableSizeInSwingThread(final Integer value,
			final int currentSize) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				fireTableRowsEvent(value, currentSize);
			}
		});
	}

	private void bindToSelectedTask(final SelectedTaskSource selectedTask) {
		selectedTask.addReceiver(new Receiver<TaskView>() {

			@Override
			public void receive(final TaskView value) {
				setTask(value);
			}

		});
	}

	private void subscribeToPeriod(final int rowIndex, final Period period) {
		final Subscriber subscriber = subscribeByRow(rowIndex);
		executor.execute(new Runnable() {
			@Override
			public void run() {
				period.subscribe(subscriber);
			}
		});
	}

	private Subscriber subscribeByRow(final int rowIndex) {
		final Integer key = Integer.valueOf(rowIndex);
		final Subscriber existing = periodSubscriberByRow.get(key); 
		if (existing != null) return existing;
		final Subscriber newcomer = newSubscriber(rowIndex);
		periodSubscriberByRow.put(key, newcomer);
		return newcomer;
	}

	Subscriber newSubscriber(final int rowIndex) {
		return new Subscriber() {
			@Override
			public void fire() {

				final Runnable fireTablerowsUpdated = new Runnable() {
					@Override
					public void run() {
						fireTableRowsUpdated(rowIndex, rowIndex);
					}
				};
				SwingUtilities.invokeLater(fireTablerowsUpdated);
			}
		};
	}

	private String formatEndTime(final Period period) {
		if (period.endTime() == null) return "";
		return formatter.formatShortTime(period.endTime());
	}

	private void enqueueSetValueAt(final Object value, final int row,
			final int column) {
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "No row to edit");
		}

		Runnable setValueAtRowColumnRunnable = new Runnable() { @Override public void run() {
				setValueAtRowColumn(value, row, column);}};
		executor.execute(setValueAtRowColumnRunnable);
	}
	
	final void setValueAtRowColumn(final Object value, final int row,
			final int column) {
		switch (column) {
		case 0:
			editPeriodDay(value, row); break;
		case 1:
			setPeriodStarting(value, row); break;
		case 2:
			setPeriodEnding(value, row); break;
		}
	}

	private void editPeriodDay(final Object value, final int row) {
		final Maybe<Date> parseDate = parseDate(value);
		if (parseDate == null) return;
		periodsSystem.editPeriodDay(_task, row, parseDate.unbox());
		
	}

	private void setPeriodStarting(final Object value, final int row) {
		final Maybe<Date> parseEditTime = parseEditTime(value, row);
		if (parseEditTime == null) return;
		tasksSystem.setPeriodStarting(_task, row, parseEditTime.unbox());
	}

	private void setPeriodEnding(final Object value, final int row) {
		final Maybe<Date> parseEditTime = parseEditTime(value, row);
		if (parseEditTime == null) return;
		tasksSystem.setPeriodEnding(_task, row, parseEditTime.unbox());
	}

	private Maybe<Date> parseDate(final Object value) {

		final String dateString = removeWeekDay(value.toString());

		try {
			return Maybe.wrap(formatter.parseShortDate(dateString));
		} catch (final ParseException e) {
			whiteborad.postMessage("Invalid date " + dateString);
			return null;
		}
	}

	private String removeWeekDay(String dateString) {
		return dateString.replaceFirst("[A-Za-z]{3}", "");
	}

	private Maybe<Date> parseEditTime(final Object value, final int row) {
		try {
			final String insertedDateString = formatter
					.formatShortDate(selectedTaskperiods.currentGet(row)
							.startTime())
					+ " " + (String) value;

			return Maybe.wrap(formatter.parseShortDateTime(insertedDateString));
		} catch (final ParseException e) {

			whiteborad.postMessage("Invalid time " + value);
			return null;
		}
	}

	final void setTask(final TaskView task) {

		this._task = task;
		fireTableDataChanged();

		if (task == null) {
			return;
		}

	}

	void changeTableSize(final Integer value) {
		final int currentSize = size.intValue();
		updateTableSizeInSwingThread(value, currentSize);
		size.set(value.intValue());
	}

	void fireTableRowsEvent(final Integer v, final int currentSize) {
		final int value = v.intValue();
		if (value > currentSize) {
			fireTableRowsInserted(size.intValue(), value - 1);
		}

		if (value < currentSize) {
			fireTableRowsDeleted(value + 1, size.intValue());
		}
	}

}