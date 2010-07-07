package ui.swing.mainScreen.tasks.summary;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import statistics.SummaryItem;

@SuppressWarnings({ "serial" })
public class SummaryTableModel extends AbstractTableModel {

	private final SummaryHoursFormat hoursFormat;

	public SummaryTableModel(final SummaryHoursFormat hoursFormat) {
		this.hoursFormat = hoursFormat;
	}

	private List<SummaryItem> items = new ArrayList<SummaryItem>();

	public int getRowCount() {
		return this.items.size();
	}

	public int getColumnCount() {
		return 3;
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final SummaryItem item = this.items.get(rowIndex);

		if (columnIndex == 0) {
			return item.getFormatedDate();
		}

		if (columnIndex == 1) {
			return item.taskName();
		}

		if (columnIndex == 2) {

			return hoursFormat.format(item.hours());
		}

		throw new RuntimeException("columnIndex out of bounds");
	}

	@Override
	public String getColumnName(final int column) {
		return new String[] { "Date", "Task", "Hours" }[column];
	}

	public final void setItems(final List<SummaryItem> items) {
		this.items = items;
		fireTableDataChanged();
	}
}