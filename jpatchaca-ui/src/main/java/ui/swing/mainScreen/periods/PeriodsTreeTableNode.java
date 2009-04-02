package ui.swing.mainScreen.periods;


interface PeriodsTreeTableNode {

	boolean isLeaf();

	PeriodsTreeTableNode getChild(int index);

	Integer childCount();

	Object getDate();
	
	public void addTreeNodeChangeListener(TreeNodeChangeListener listener);

}
