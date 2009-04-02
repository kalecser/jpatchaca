package ui.swing.mainScreen.periods;

import java.util.LinkedHashMap;
import java.util.Map;

import tasks.TaskSignal;

public class TaskTreeNode implements PeriodsTreeTableNode {

	private final TaskSignal task;
	private final Map<Integer, TaskYearNode> treeYearsNodesByIndex;

	public TaskTreeNode(TaskSignal selectedTaskSignal) {
		this.task = selectedTaskSignal;
		treeYearsNodesByIndex = new LinkedHashMap<Integer, TaskYearNode>();
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Integer childCount() {
		return task.years().currentSize().value();
	}

	@Override
	public PeriodsTreeTableNode getChild(int index) {
		int year = task.years().currentList().value().get(index);
		if (treeYearsNodesByIndex.get(year) == null)
			treeYearsNodesByIndex.put(year, new TaskYearNode(task.years()
					.currentList().value().get(index)));
		return treeYearsNodesByIndex.get(year);
	}

	@Override
	public Object getDate() {
		return null;
	}

	@Override
	public void addTreeNodeChangeListener(final TreeNodeChangeListener listener) {
		
	}


}
