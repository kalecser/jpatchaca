/**
 * 
 */
package ui.swing.mainScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.SwingUtilities;

import tasks.TasksByNameComparator;
import tasks.tasks.TaskView;

class TaskListListModel extends AbstractListModel {
	private static final long serialVersionUID = 1L;
	private final List<TaskView> tasks = new ArrayList<TaskView>();

	@Override
	public Object getElementAt(final int index) {

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
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final int oldSize = TaskListListModel.this.tasks.size();
				TaskListListModel.this.tasks.clear();
				TaskListListModel.this.tasks.addAll(tasks);
				Collections.sort(tasks, new TasksByNameComparator());

				final int newSize = TaskListListModel.this.tasks.size();
				if (oldSize > newSize) {
					fireIntervalRemoved(this, oldSize - newSize - 1,
							oldSize - 1);
				}

				fireContentsChanged(this, 0, newSize - 1);
			}
		});
	}

}