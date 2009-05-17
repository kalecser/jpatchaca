package ui.swing.tasks;

import java.util.List;

import tasks.delegates.StartTaskDataParser;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TasksView;
import basic.NonEmptyString;

public class StartTaskScreenModelImpl implements StartTaskScreenModel {

	private final TasksView tasks;
	private final StartTaskDelegate startTaskDelegate;
	private final StartTaskDataParser parser;

	public StartTaskScreenModelImpl(final StartTaskDelegate startTaskDelegate,
			final TasksView tasks, final StartTaskDataParser parser) {
		this.startTaskDelegate = startTaskDelegate;
		this.tasks = tasks;
		this.parser = parser;
	}

	@Override
	public void startTask(final String taskName) {
		if (taskName == null) {
			return;
		}

		if (taskName.isEmpty()) {
			return;
		}

		final NonEmptyString nonEmptyTaskName = new NonEmptyString(taskName);
		startTaskDelegate.starTask(parser.parse(nonEmptyTaskName));
	}

	@Override
	public List<String> taskNames() {
		return tasks.taskNames();
	}
}
