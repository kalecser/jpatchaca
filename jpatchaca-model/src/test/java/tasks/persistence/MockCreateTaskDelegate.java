package tasks.persistence;

import tasks.delegates.CreateTaskDelegate;
import tasks.home.TaskData;

public class MockCreateTaskDelegate extends CreateTaskDelegate {

	private String taskname = null;

	@Override
	public void createTask(final TaskData taskData) {
		taskname = taskData.getTaskName();
		super.createTask(taskData);
	}

	public boolean taskCreated() {
		return taskname != null;
	}

	public String taskName() {
		return taskname;
	}

}
