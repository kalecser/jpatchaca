/**
 * 
 */
package ui.swing.mainScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

import lang.Maybe;
import tasks.TaskView;
import tasks.TasksByNameComparator;

class TaskListListModel extends AbstractListModel {
	private static final long serialVersionUID = 1L;
	private final List<TaskView> tasks = new ArrayList<TaskView>();

	@Override
	public TaskView getElementAt(final int index) {

		if (index >= tasks.size()) {
			return null;
		}

		return tasks.get(index);
	}

	@Override
	public int getSize() {
		return tasks.size();
	}

	public void setTasks(final List<TaskView> tasks) {

		final int oldSize = TaskListListModel.this.tasks.size();
		TaskListListModel.this.tasks.clear();
		TaskListListModel.this.tasks.addAll(tasks);
		Collections.sort(TaskListListModel.this.tasks,
				new TasksByNameComparator());

		final int newSize = TaskListListModel.this.tasks.size();
		if (oldSize > newSize) {
			fireIntervalRemoved(this, oldSize - newSize - 1, oldSize - 1);
		}

		fireContentsChanged(this, 0, newSize - 1);
	}

	public Maybe<TaskView> getTaskByName(final String selectedTask) {

		for (final TaskView task : tasks) {
			if (task.name().equals(selectedTask)) {
				return Maybe.wrap(task);
			}
		}
		return null;
	}

}