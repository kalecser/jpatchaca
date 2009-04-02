package ui.swing.mainScreen.periods;

interface TreeNodeChangeListener {

	void childRemoved(PeriodsTreeTableNode taskTreeNode, int index);
	void childAdded(TaskTreeNode taskTreeNode);

}
