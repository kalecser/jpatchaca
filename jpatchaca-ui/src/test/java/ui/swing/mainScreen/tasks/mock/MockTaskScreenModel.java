package ui.swing.mainScreen.tasks.mock;

import org.apache.commons.lang.NotImplementedException;

import tasks.TaskView;
import tasks.home.TaskData;
import ui.swing.mainScreen.tasks.TaskScreenModel;

public class MockTaskScreenModel implements TaskScreenModel {

	@Override
	public TaskView selectedTask() {
		throw new NotImplementedException();
	}

	@Override
	public void createTask(TaskData data) {
		throw new NotImplementedException();
	}

	@Override
	public void createTaskAndStart(TaskData data, Long unbox) {
		throw new NotImplementedException();
	}

	@Override
	public void editTask(TaskView taskView, TaskData data) {
		throw new NotImplementedException();
	}

}
