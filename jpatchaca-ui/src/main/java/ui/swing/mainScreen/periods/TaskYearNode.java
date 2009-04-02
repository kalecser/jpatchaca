package ui.swing.mainScreen.periods;

import org.apache.commons.lang.NotImplementedException;


public class TaskYearNode implements PeriodsTreeTableNode {

	private final Integer year;

	public TaskYearNode(Integer currentGet) {
		this.year = currentGet;
	}
	
	@Override
	public Integer childCount() {
		return 0;
	}

	@Override
	public PeriodsTreeTableNode getChild(int index) {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
	@Override
	public Object getDate() {
		return year;
	}

	@Override
	public void addTreeNodeChangeListener(TreeNodeChangeListener listener) {
		throw new NotImplementedException();		
	}

}
