/**
 * 
 */
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
import basic.Formatter;
import basic.Subscriber;

public class PeriodsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final String[] columnNames = new String[] { "Day", "Start",
			"End", "Time" };
	private static int[] editableColumns = new int[] { 0, 1, 2 };

	private final TasksSystem tasksSystem;
	private final PeriodsInTasksSystem periodsSystem;
	private final Formatter formatter;

	private TaskView _task = null;
	private final SelectedTaskPeriods selectedTaskperiods;

	Map<Integer, Subscriber> periodSubscriberByRow = new LinkedHashMap<Integer, Subscriber>();

	private final AtomicInteger size = new AtomicInteger(0);

	public PeriodsTableModel(final TasksSystem tasksSystem,
			final PeriodsInTasksSystem periodsSystem,
			final Formatter formatter, final SelectedTaskSource selectedTask,
			final SelectedTaskPeriods selectedTaskperiods) {
		this.tasksSystem = tasksSystem;
		this.periodsSystem = periodsSystem;
		this.formatter = formatter;
		this.selectedTaskperiods = selectedTaskperiods;

		bindToSelectedTask(selectedTask);

		selectedTaskperiods.size().addReceiver(new Receiver<Integer>() {
			@Override
			public void receive(final Integer value) {

				final int currentSize = size.intValue();

				updateTableSizeInSwingThread(value, currentSize);

				size.set(value);
			}

			private void updateTableSizeInSwingThread(final Integer value,
					final int currentSize) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (value > currentSize) {
							fireTableRowsInserted(size.intValue(), value - 1);
						}

						if (value < currentSize) {
							fireTableRowsDeleted(value + 1, size.intValue());
						}
					}
				});
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

	public int getRowCount() {
		return this.selectedTaskperiods.currentSize();
	}

	public int getColumnCount() {
		return PeriodsTableModel.columnNames.length;
	}

	public synchronized Object getValueAt(final int rowIndex,
			final int columnIndex) {

		final Period period = selectedTaskperiods.currentGet(rowIndex);

		if (!periodSubscriberByRow.containsKey(rowIndex)) {
			periodSubscriberByRow.put(rowIndex, new Subscriber() {
				@Override
				public void fire() {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							fireTableRowsUpdated(rowIndex, rowIndex);
						}
					});
				}
			});
		}

		period.subscribe(periodSubscriberByRow.get(rowIndex));

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

	private String formatEndTime(final Period period) {
		if (period.endTime() != null) {
			return formatter.formatShortTime(period.endTime());
		} else {
			return "";
		}
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

	private void enqueueSetValueAt(final Object value, final int row,
			final int column) {
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "No row to edit");
		}

		new Thread() {
			@Override
			public void run() {

				if (column == 0) {
					final Maybe<Date> parseDate = parseDate(value);
					if (parseDate != null) {
						periodsSystem.editPeriodDay(_task, row, parseDate
								.unbox());
					}
				}

				if (column == 1) {

					final Maybe<Date> parseEditTime = parseEditTime(value, row);
					if (parseEditTime != null) {
						tasksSystem.setPeriodStarting(_task, row, parseEditTime
								.unbox());
					}
				}

				if (column == 2) {
					final Maybe<Date> parseEditTime = parseEditTime(value, row);
					if (parseEditTime != null) {
						tasksSystem.setPeriodEnding(_task, row, parseEditTime
								.unbox());
					}
				}
			}
		}.start();
	}

	private Maybe<Date> parseDate(final Object value) {

		final String dateString = removeWeekDay(value.toString());

		try {
			return Maybe.wrap(formatter.parseShortDate(dateString));
		} catch (final ParseException e) {
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
			final String insertedDateString = formatter
					.formatShortDate(selectedTaskperiods.currentGet(row)
							.startTime())
					+ " " + (String) value;

			return Maybe.wrap(formatter.parseShortDateTime(insertedDateString));
		} catch (final ParseException e) {
			JOptionPane.showMessageDialog(null, "Invalid time " + value);
			return null;
		}
	}

	private final void setTask(final TaskView task) {

		this._task = task;
		fireTableDataChanged();

		if (task == null) {
			return;
		}

	}

	public Period getPeriod(final int selectedRow) {
		return selectedTaskperiods.currentGet(selectedRow);
	}

}