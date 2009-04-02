/**
 * 
 */
package ui.swing.mainScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

import tasks.TasksByNameComparator;
import tasks.tasks.TaskView;

class TaskListListModel extends AbstractListModel{
	private static final long serialVersionUID = 1L;
	private final List<TaskView> tasks = new ArrayList<TaskView>();

	@Override
	public Object getElementAt(int index) {
		return tasks.get(index);
	}

	@Override
	public int getSize() {
		return tasks.size();
	}

	public void setTasks(List<TaskView> tasks) {
		
		int oldSize = this.tasks.size();
		this.tasks.clear();
		this.tasks.addAll(tasks);
		Collections.sort(tasks, new TasksByNameComparator());
		
		int newSize = this.tasks.size();
		if (oldSize > newSize)
			fireIntervalRemoved(this, oldSize - newSize - 1, oldSize - 1);
		
		fireContentsChanged(this, 0, newSize - 1);
		
		
	}


}