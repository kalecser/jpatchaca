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
import org.reactive.Receiver;

import periods.Period;
import periods.PeriodsListener;
import periodsInTasks.PeriodsInTasksSystem;
import tasks.TasksSystem;
import tasks.tasks.TaskView;
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
	private PeriodsListener periodsListener;
	private final Formatter formatter;

	private TaskView _task = null;

	public PeriodsTableModel(final TasksSystem tasksSystem,
			final PeriodsInTasksSystem periodsSystem,
			final Formatter formatter, final SelectedTaskSource selectedTask) {
		this.tasksSystem = tasksSystem;
		this.periodsSystem = periodsSystem;
		this.formatter = formatter;

		bindToSelectedTask(selectedTask);

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
		if (this._task == null) {
			return 0;
		}
		return this._task.periods().size();
	}

	public Period getPeriodAt(final int rowIndex) {
		return periodForRowIndex(rowIndex);
	}

	public int getColumnCount() {
		return PeriodsTableModel.columnNames.length;
	}

	public synchronized Object getValueAt(final int rowIndex,
			final int columnIndex) {

		if (this._task == null) {
			return "";
		}

		final Period period = periodForRowIndex(rowIndex);

		if (columnIndex == 0) {
			return formatter.formatShortDateWithWeekday(period.startTime());
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

	private Period periodForRowIndex(final int rowIndex) {

		return _task.periodAt(rowToIndex(rowIndex));
	}

	private int rowToIndex(final int row) {
		final int periodsCount = _task.periodsCount();
		if (periodsCount == 0) {
			throw new RuntimeException("No periods in task");
		}
		return periodsCount - row - 1;
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

		final int rowToIndex = rowToIndex(row);
		enqueueSetValueAt(value, row, column, rowToIndex);

	}

	private void enqueueSetValueAt(final Object value, final int row,
			final int column, final int rowToIndex) {
		if (rowToIndex == -1) {
			JOptionPane.showMessageDialog(null, "No row to edit");
		}

		new Thread() {
			@Override
			public void run() {

				if (column == 0) {
					final Maybe<Date> parseDate = parseDate(value);
					if (parseDate != null) {
						periodsSystem.editPeriodDay(_task, rowToIndex,
								parseDate.unbox());
					}
				}

				if (column == 1) {

					final Maybe<Date> parseEditTime = parseEditTime(value, row);
					if (parseEditTime != null) {
						tasksSystem.setPeriodStarting(_task, rowToIndex,
								parseEditTime.unbox());
					}
				}

				if (column == 2) {
					final Maybe<Date> parseEditTime = parseEditTime(value, row);
					if (parseEditTime != null) {
						tasksSystem.setPeriodEnding(_task, rowToIndex,
								parseEditTime.unbox());
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
					.formatShortDate(periodForRowIndex(row).startTime())
					+ " " + (String) value;

			return Maybe.wrap(formatter.parseShortDateTime(insertedDateString));
		} catch (final ParseException e) {
			JOptionPane.showMessageDialog(null, "Invalid time " + value);
			return null;
		}
	}

	private final void setTask(final TaskView task) {

		if (this._task != null) {
			this._task.removePeriodListener(periodsListener);
		}

		this._task = task;
		fireTableDataChanged();

		if (task == null) {
			return;
		}

		if (periodsListener != null) {
			periodsListener.clean();
		}

		periodsListener = createPeriodsListener();
		task.addPeriodsListener(periodsListener);
	}

	private PeriodsListener createPeriodsListener() {
		return new PeriodsListener() {
			private final Map<Period, Subscriber> subscribers = new HashMap<Period, Subscriber>();

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

			private synchronized void removeSubscriber(final Period period) {
				final Subscriber subscriber = subscribers.remove(period);
				if (subscriber != null) {
					period.unsubscribe(subscriber);
				}
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
				for (final Period period : new HashSet<Period>(subscribers
						.keySet())) {
					removeSubscriber(period);
				}

			}
		};
	}

}