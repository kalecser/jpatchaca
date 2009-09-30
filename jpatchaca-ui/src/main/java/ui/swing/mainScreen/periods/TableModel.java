package ui.swing.mainScreen.periods;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import lang.Maybe;
import reactive.ListSignal;

public class TableModel<T> extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final List<TableModelColumn<T>> columns;
	private final ListSignal<T> rows;

	public TableModel(final ListSignal<T> rows) {
		columns = new ArrayList<TableModelColumn<T>>();
		this.rows = rows;
	}

	public void addColumn(final TableModelColumn<T> column) {
		columns.add(column);
		fireTableStructureChanged();
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public String getColumnName(final int column) {
		return columns.get(column).name();
	}

	@Override
	public int getRowCount() {
		return rows.currentSize();
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final TableModelColumn<T> tableModelColumn = columns.get(columnIndex);

		final Maybe<T> row = rows.currentGet(rowIndex);
		if (row == null) {
			return "deleted";
		}
		return tableModelColumn.getFunctor().evaluate(row.unbox())
				.currentValue();
	}

}
