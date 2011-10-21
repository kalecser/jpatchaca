package ui.swing.mainScreen.tasks.day;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import jira.JiraWorklogOverride;
import tasks.TasksSystem;
import basic.Formatter;

public class DayTasksTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Pair> items = new ArrayList<Pair>();
	private final Formatter formatter;
	private final TasksSystem tasksSystem;
	private final DayTaskTableModelCellValue[] cellValues;
	private final JiraWorklogOverride worklogOverride;

	public DayTasksTableModel(final Formatter formatter,
			final TasksSystem tasksSystem, JiraWorklogOverride worklogOverride) {
		this.formatter = formatter;
		this.tasksSystem = tasksSystem;
		this.worklogOverride = worklogOverride;
		cellValues = DayTaskTableModelCellValue.values();
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
			tasksSystem.setPeriodStarting(item.task(),
					item.periodIndex(), dateParsed);
		}
	}

	private void editWorklogOverride(Object value, Pair item) {
		worklogOverride.overrideTimeSpentForPeriod(value.toString(), item
				.period());
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

		fireTableDataChanged();
	}
}
