package ui.swing.mainScreen;

import ui.swing.tasks.StartTaskController;

public class TaskListModel {

	private final StartTaskController startTaskController;

	public TaskListModel(final StartTaskController startTaskController) {
		this.startTaskController = startTaskController;
	}

	public void startTask() {
		startTaskController.show();
	}

}
