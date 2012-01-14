package ui.swing.mainScreen.tasks.day;

import java.text.ParseException;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import jira.JiraWorklogOverride;
import tasks.TasksSystem;
import basic.Formatter;
import basic.Subscriber;

public class DayTasksTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	//private List<Pair> items = new ArrayList<Pair>();
	private final Formatter formatter;
	private final TasksSystem tasksSystem;
	private final DayTaskTableModelCellValue[] cellValues;
	private final JiraWorklogOverride worklogOverride;
	private final DayTasksListModel dayTaskListModel;

	public DayTasksTableModel(final Formatter formatter,
			final TasksSystem tasksSystem, JiraWorklogOverride worklogOverride,
			DayTasksListModel dayTaskListModel) {
		this.formatter = formatter;
		this.tasksSystem = tasksSystem;
		this.worklogOverride = worklogOverride;
		this.dayTaskListModel = dayTaskListModel;
		cellValues = DayTaskTableModelCellValue.values();
		dayTaskListModel.addChangeSubscriber(new Subscriber() {
			@Override
			public void fire() {
				fireTableDataChanged();
			}
		});
	}
	
	@Override
	public int getRowCount() {
		return dayTaskListModel.getWorklogList().size();
	}

	@Override
	public int getColumnCount() {
		return cellValues.length;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final Pair item = dayTaskListModel.getWorklogList().get(rowIndex);
		return cellValues[columnIndex].getValue(item);
	}

	@Override
	public String getColumnName(final int column) {
		return cellValues[column].getLabel();
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return cellValues[columnIndex].isEditable();
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex,
			final int columnIndex) {
		final Pair item = dayTaskListModel.getWorklogList().get(rowIndex);
		enqueueSetValueAt(aValue, cellValues[columnIndex], item);
	}

	private void enqueueSetValueAt(final Object value,
			final DayTaskTableModelCellValue column, final Pair item) {

		new Thread() {
			@Override
			public void run() {
				editCell(column, value, item);
			}
		}.start();
	}

	private void editPeriodEnd(final Object value, Pair item) {
		Date dateParsed;
		dateParsed = editDate(item.period().endTime(), (String) value);

		if (dateParsed != null) {
			tasksSystem.setPeriodEnding(item.task(), item.periodIndex(),
					dateParsed);
		}
	}

	private void editPeriodStart(final Object value, Pair item) {
		Date dateParsed;
		dateParsed = editDate(item.period().startTime(), (String) value);

		if (dateParsed != null) {
			tasksSystem.setPeriodStarting(item.task(), item.periodIndex(),
					dateParsed);
		}
	}

	private void editWorklogOverride(Object value, Pair item) {
		worklogOverride.overrideTimeSpentForPeriod(value.toString(),
				item.period());
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

	void editCell(final DayTaskTableModelCellValue column, final Object value,
			final Pair item) {
		
		switch (column) {
		case Start:
			editPeriodStart(value, item);
			break;

		case End:
			editPeriodEnd(value, item);
			break;

		case ToSend:
			editWorklogOverride(value, item);
			break;

		default:
			// Nothing to do
		}

		dayTaskListModel.fireChange();
	}
}
