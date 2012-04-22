package ui.cli.mock;

import ui.swing.tasks.StartTask;

class StartTaskMock implements StartTask{

	private String task = "";

	@Override
	public void startTask(String task) {
		this.task = task;
	}

	public String getStartedTask() {
		return task;
	}

}
