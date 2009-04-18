/*
 * Created on 17/04/2009
 */
package ui.swing.mainScreen;

import ui.swing.tasks.StartTaskController;

public final class TaskListModelImpl implements TaskListModel {

	private final StartTaskController startTaskController;

	public TaskListModelImpl(final StartTaskController startTaskController) {
		this.startTaskController = startTaskController;
	}

	public void startTask() {
		startTaskController.show();
	}

}
