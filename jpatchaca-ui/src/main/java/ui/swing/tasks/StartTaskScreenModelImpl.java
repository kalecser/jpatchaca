package ui.swing.tasks;

import java.util.List;

import tasks.delegates.StartTaskByNameDelegate;
import tasks.tasks.TasksView;
import basic.NonEmptyString;

public class StartTaskScreenModelImpl implements StartTaskScreenModel {

	private final TasksView tasks;
	private final StartTaskByNameDelegate startTaskDelegate;

	public StartTaskScreenModelImpl(
			final StartTaskByNameDelegate startTaskDelegate,
			final TasksView tasks) {
		this.startTaskDelegate = startTaskDelegate;
		this.tasks = tasks;
	}

	@Override
	public void startTask(final String taskName) {
		if (taskName == null) {
			return;
		}

		if (taskName.isEmpty()) {
			return;
		}

		startTaskDelegate.starTask(new NonEmptyString(taskName));
	}

	@Override
	public List<String> taskNames() {
		return tasks.taskNames();
	}
}
