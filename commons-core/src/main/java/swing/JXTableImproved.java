package swing;

import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;

public final class JXTableImproved extends JXTable {
	private static final long serialVersionUID = 1L;

	public JXTableImproved() {
		super();
	}

	public JXTableImproved(int numRows, int numColumns) {
		super(numRows, numColumns);
	}

	public JXTableImproved(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}

	public JXTableImproved(TableModel dm, TableColumnModel cm,
			ListSelectionModel sm) {
		super(dm, cm, sm);
	}

	public JXTableImproved(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
	}

	public JXTableImproved(TableModel dm) {
		super(dm);
	}
	

	public final static String EXCLUDE = "F2";
	
	private boolean isBlankEditor = false;
	
	
	@Override
	public Component prepareEditor(TableCellEditor editor, int row, int column) {
		Component c = super.prepareEditor(editor, row, column);
		
		if (isBlankEditor)
			((JTextField) c).setText("");
		
		return c;
	}

	@Override
	protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
		if (! EXCLUDE.equals(KeyEvent.getKeyText(e.getKeyCode())))
			isBlankEditor = true;
		
		boolean retValue = super.processKeyBinding(ks, e, condition, pressed);
		
		isBlankEditor = false;
		return retValue;
	}


}
