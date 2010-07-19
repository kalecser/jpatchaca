package ui.swing.mainScreen.tasks.day;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.time.DateUtils;

import periods.Period;
import tasks.TaskView;
import tasks.TasksSystem;
import basic.Formatter;

public class DayTasksTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Pair> items = new ArrayList<Pair>();
	private final Formatter formatter;
	private final TasksSystem tasksSystem;
	private final CellValue[] cellValues;

	public DayTasksTableModel(final Formatter formatter,
			final TasksSystem tasksSystem) {
		this.formatter = formatter;
		this.tasksSystem = tasksSystem;
		CellValue.formatter = formatter;
		cellValues = new CellValue[] { CellValue.TaskName,
				CellValue.WorklogStatus, CellValue.Start, CellValue.End,
				CellValue.Total };
	}

	@Override
	public int getRowCount() {
		return this.items.size();
	}

	@Override
	public int getColumnCount() {
		return cellValues.length;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final Pair item = this.items.get(rowIndex);
		return cellValues[columnIndex].getValue(item);
	}

	@Override
	public String getColumnName(final int column) {
		return cellValues[column].getLabel();
	}

	public final void setItems(final List<Pair> items) {
		this.items = items;
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return cellValues[columnIndex].isEditable();
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex,
			final int columnIndex) {
		final Pair item = this.items.get(rowIndex);
		enqueueSetValueAt(aValue, item.getPeriod(), columnIndex, item.getTask());
	}

	private void enqueueSetValueAt(final Object value, final Period period,
			final int column, final TaskView task) {

		new Thread() {
			@Override
			public void run() {

				Date dateParsed = null;

				switch (column) {
				case 2:
					dateParsed = editDate(period.startTime(), (String) value);

					if (dateParsed != null) {
						tasksSystem.setPeriodStarting(task, task
								.getPeriodIndex(period), dateParsed);
					}
					break;

				case 3:
					dateParsed = editDate(period.endTime(), (String) value);

					if (dateParsed != null) {
						tasksSystem.setPeriodEnding(task, task
								.getPeriodIndex(period), dateParsed);
					}
					break;
				}

				fireTableDataChanged();
			}
		}.start();
	}

	private Date editDate(final Date date, final String newHour) {
		final String timeFormatted = formatter.formatShortDate(date) + " "
				+ newHour;
		Date dateParsed = null;
		try {
			dateParsed = formatter.parseShortDateTime(timeFormatted);
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return dateParsed;
	}

	public static class Pair implements Comparable<Pair> {
		private final TaskView task;
		private final Period period;

		public Pair(final TaskView task, final Period period) {
			this.task = task;
			this.period = period;
		}

		public TaskView getTask() {
			return task;
		}

		public Period getPeriod() {
			return period;
		}

		@Override
		public int compareTo(final Pair o) {
			final Pair outroPar = o;

			return this.getPeriod().startTime().compareTo(
					outroPar.getPeriod().startTime());
		}

	}

	private enum CellValue {
		TaskName {
			@Override
			public String getLabel() {
				return "Task";
			}

			@Override
			public Object getValue(final Pair item) {
				return item.getTask().name();
			}
		},

		WorklogStatus {
			@Override
			public String getLabel() {
				return "Worklog status";
			}

			@Override
			public Object getValue(final Pair item) {
				if (item.getTask().getJiraIssue() == null) {
					return "no issue";
				} else if (item.getPeriod().isWorklogSent()) {
					return "sent";
				} else {
					return "not sent";
				}
			}
		},
		Start {
			@Override
			public String getLabel() {
				return "Start";
			}

			@Override
			public Object getValue(final Pair item) {
				return formatter.formatShortTime(item.getPeriod().startTime());
			}

			@Override
			public boolean isEditable() {
				return true;
			}
		},
		End {
			@Override
			public String getLabel() {
				return "End";
			}

			@Override
			public Object getValue(final Pair item) {
				if (item.getPeriod().endTime() != null) {
					return formatter
							.formatShortTime(item.getPeriod().endTime());
				}
				return "";
			}

			@Override
			public boolean isEditable() {
				return true;
			}
		},
		Total {
			@Override
			public String getLabel() {
				return "Total";
			}

			@Override
			public Object getValue(final Pair item) {
				final NumberFormat format = new DecimalFormat("#0.00");
				return format.format(item.getPeriod().getMiliseconds()
						/ DateUtils.MILLIS_PER_HOUR);
			}
		};

		public static Formatter formatter;

		public abstract String getLabel();

		public abstract Object getValue(Pair item);

		public boolean isEditable() {
			return false;
		}
	}
}
