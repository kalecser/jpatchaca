package ui.swing.mainScreen.tasks.mock;

import basic.Delegate.Listener;
import tasks.delegates.CreateTaskDelegate;
import tasks.home.TaskData;

public class CreateTaskDelegateMock implements CreateTaskDelegate {

	@Override
	public void createTask(TaskData ignored) {}

	@Override
	public void addListener(Listener<TaskData> listener) {
		throw new java.lang.RuntimeException("Not implemented");
	}

}
