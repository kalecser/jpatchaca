package tasks.persistence;

import labels.labels.SelectedLabel;
import tasks.delegates.CreateTaskDelegate;
import tasks.home.TaskData;

public class MockCreateTaskDelegate extends CreateTaskDelegate {

	public MockCreateTaskDelegate(SelectedLabel selectedLabel) {
		super(new SelectedLabel());
	}

	private String taskname = null;
	private String labelName;

	@Override
	public void createTask(final TaskData taskData) {
		taskname = taskData.getTaskName();
		labelName = taskData.getLabel();
		super.createTask(taskData);
	}

	public boolean taskCreated() {
		return taskname != null;
	}

	public String taskName() {
		return taskname;
	}

	public String labelName() {
		return labelName;
	}

}
