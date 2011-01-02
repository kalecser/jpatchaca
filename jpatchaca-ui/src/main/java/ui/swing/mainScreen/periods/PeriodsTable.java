package ui.swing.mainScreen.periods;

import java.awt.Font;

import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.SortOrder;

public class PeriodsTable extends JXTable{
	private static final long serialVersionUID = 1L;

	public PeriodsTable(PeriodsTableModel periodsTableModel) {
		super(periodsTableModel);
		
		setFont(Font.decode(Font.MONOSPACED));
		this.getSelectionModel().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		setSortable(true);
		setSortOrder(0, SortOrder.DESCENDING);
	}

}
