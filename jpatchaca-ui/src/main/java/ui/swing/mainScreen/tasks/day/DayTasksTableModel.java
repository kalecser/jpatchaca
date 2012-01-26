package ui.swing.mainScreen.tasks.day;


import javax.swing.table.AbstractTableModel;

import basic.Subscriber;

public class DayTasksTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private final DayTaskTableModelCellValue[] cellValues;
	private final DayTasksListModel dayTasksListModel;

	public DayTasksTableModel(DayTasksListModel dayTasksListModel) {
		this.dayTasksListModel = dayTasksListModel;
        cellValues = DayTaskTableModelCellValue.values();
		dayTasksListModel.addChangeSubscriber(new Subscriber() {
			@Override
			public void fire() {
				fireTableDataChanged();
			}
		});
	}
	
	@Override
	public int getRowCount() {
		return dayTasksListModel.getWorklogList().size();
	}

	@Override
	public int getColumnCount() {
		return cellValues.length;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final TaskWorklog item = dayTasksListModel.getWorklogList().get(rowIndex);
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
		final TaskWorklog item = dayTasksListModel.getWorklogList().get(rowIndex);
		enqueueSetValueAt(aValue, cellValues[columnIndex], item);
	}

	private void enqueueSetValueAt(final Object value,
			final DayTaskTableModelCellValue column, final TaskWorklog item) {

		new Thread() {
			@Override
			public void run() {
				editCell(column, value, item);
			}
		}.start();
	}

	void editCell(final DayTaskTableModelCellValue column, final Object value,
			final TaskWorklog item) {
		switch (column) {
		case Start:
			item.editPeriodStart(value.toString());
			break;

		case End:
			item.editPeriodEnd(value.toString());
			break;

		case ToSend:
			item.overrideTimeSpent(value.toString());
			break;

		default:
			// Nothing to do
		}

		dayTasksListModel.fireChange();
	}
}
