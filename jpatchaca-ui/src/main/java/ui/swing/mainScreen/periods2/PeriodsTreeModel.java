package ui.swing.mainScreen.periods2;

import org.apache.commons.lang.Validate;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

public class PeriodsTreeModel extends AbstractTreeTableModel implements TreeTableModel {

	
	String[] columns = new String[]{"Period"};
	private final PeriodsTreeItem root;
	
	
	public PeriodsTreeModel(final PeriodsTreeItem root){
		
		Validate.notNull(root, "root must not be null");
		
		this.root = root;		
	}
	
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}	
	
	@Override
	public String getColumnName(final int column) {
		return columns[column];
	}
	
	
	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getValueAt(final Object arg0, final int arg1) {
		
		final PeriodsTreeItem item = (PeriodsTreeItem) arg0;
		
		
		switch (arg1){
		case 0:
			return item.caption();
		case 1:
			return "hours";
		default:
			return "none";
		}
	}

	
	@Override
	public int getChildCount(final Object parent) {
		return ((PeriodsTreeItem)parent).children().length;
	}

	@Override
	public int getIndexOfChild(final Object parent, final Object child) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Object getChild(final Object parent, final int index) {
		return ((PeriodsTreeItem)parent).children()[index];
	}


	public void refresh() {
		modelSupport.fireNewRoot();
	}

	}
