package ui.swing.mainScreen.periods;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.SortOrder;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.StringValue;

import periods.Period;

public class PeriodsTable extends JXTable{
	private static final long serialVersionUID = 1L;
	
	private final PeriodsTableModel periodsTableModel;

	public PeriodsTable(PeriodsTableModel periodsTableModel) {
		super(periodsTableModel);
		this.periodsTableModel = periodsTableModel;
		
		setFont(Font.decode(Font.MONOSPACED));
		this.getSelectionModel().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		setSortable(true);
		setSortOrder(0, SortOrder.DESCENDING);
		
		adjustColumnsAppearance();
	}

	public List<Period> selectedPeriods() {
		
		final int[] selectedRows = this.getSelectedRows();
		if (selectedRows == null) return new ArrayList<Period>();
	
		final boolean noneSelected = 
				selectedRows.length == 0 || selectedRows[0] == -1;
		if (noneSelected) {
			return new ArrayList<Period>();
		}
	
		final List<Period> selectedPeriods = new ArrayList<Period>();
		for (final int index : selectedRows) {
			selectedPeriods.add(periodsTableModel.getPeriod(this
					.convertRowIndexToModel(index)));
		}
	
		return selectedPeriods;
	}

	private void adjustColumnsAppearance() {
		final TableColumnModel model = getColumnModel();
		final int smallSize = 100;
		final int mediumSize = 200;
		model.getColumn(0).setPreferredWidth(mediumSize);
		model.getColumn(0).setMaxWidth(mediumSize);
		model.getColumn(1).setPreferredWidth(smallSize);
		model.getColumn(1).setMaxWidth(smallSize);
		model.getColumn(2).setPreferredWidth(smallSize);
		model.getColumn(2).setMaxWidth(smallSize);
		model.getColumn(3).setPreferredWidth(smallSize);
		model.getColumn(3).setMaxWidth(smallSize);
		model.getColumn(3).setCellRenderer(
				new DefaultTableRenderer(StringValue.TO_STRING,
						SwingConstants.RIGHT));
	}
}
