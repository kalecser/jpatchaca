package ui.swing.mainScreen.tasks.worklog;

import javax.swing.table.AbstractTableModel;

import basic.Subscriber;

public class WorklogTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final WorklogTableCell[] cellValues;
    private final WorklogListModel dayTasksListModel;

    public WorklogTableModel(WorklogListModel dayTasksListModel) {
        this.dayTasksListModel = dayTasksListModel;
        cellValues = WorklogTableCell.values();
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
        final Worklog item = dayTasksListModel.getWorklogList().get(rowIndex);
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
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        final Worklog item = dayTasksListModel.getWorklogList().get(rowIndex);
        enqueueSetValueAt(aValue, cellValues[columnIndex], item);
    }

    private void enqueueSetValueAt(final Object value, final WorklogTableCell column,
            final Worklog item) {

        new Thread() {
            @Override
            public void run() {
                editCell(column, value, item);
            }
        }.start();
    }

    private void editCell(final WorklogTableCell column, final Object value, final Worklog item) {
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
