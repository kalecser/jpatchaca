package ui.swing.mainScreen.periods;

import javax.swing.tree.TreePath;

import org.apache.commons.lang.NotImplementedException;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import ui.swing.mainScreen.TaskList;

public class PeriodsTreeTableModel extends AbstractTreeTableModel {

	private static final String EMPTY_ROOT = "root";
	String[] columnNames = new String[]{"Year", "day"};
	private TaskTreeNode taskNode;
	
	public PeriodsTreeTableModel(TaskList taskList){
		
				throw new NotImplementedException();
//				taskNode = new TaskTreeNode(null);
//				
//				taskNode.addTreeNodeChangeListener(new TreeNodeChangeListener() {
//				
//					@Override
//					public void childRemoved(PeriodsTreeTableNode taskTreeNode, int index) {
//						modelSupport.fireChildRemoved(new TreePath(taskTreeNode), index, taskTreeNode.getChild(index));				
//					}
//				
//					@Override
//					public void childAdded(TaskTreeNode taskTreeNode) {
//						int index = taskTreeNode.childCount() + 1;
//						modelSupport.fireChildAdded(new TreePath(taskTreeNode), index, taskTreeNode.getChild(index));
//					}
//				});
//			
		
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public Object getValueAt(Object node, int column) {
		// TODO Auto-generated method stub
		return ((PeriodsTreeTableNode)node).getDate();
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValueAt(Object value, Object node, int column) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Object getChild(Object node, int index) {		
		return ((PeriodsTreeTableNode)node).getChild(index);
	}

	@Override
	public int getChildCount(Object node) {
		if (node == root)
			return 1;
		if (node == taskNode)
			return ((PeriodsTreeTableNode)node).childCount();
		
		return 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getRoot() {
		if (taskNode == null)
			return EMPTY_ROOT;
		return taskNode;
	}

	@Override
	public boolean isLeaf(Object node) {
		if (node == EMPTY_ROOT )
			return true;
		
		return ((PeriodsTreeTableNode)node).isLeaf();
	}


	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		
	}
	
		
}
