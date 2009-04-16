package tasks.persistence;

import org.picocontainer.Startable;
import org.reactivebricks.commons.lang.Maybe;

import tasks.delegates.CreateTaskDelegate;
import tasks.delegates.StartTaskByNameDelegate;
import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import tasks.tasks.TasksView;
import ui.swing.mainScreen.Delegate;
import basic.NonEmptyString;

public class StartTaskByNamePersistence implements Startable {

	private final TasksView tasks;
	private final StartTaskDelegate startTaskDelegate;
	private final StartTaskByNameDelegate startTaskByNameDelegate;
	private final CreateTaskDelegate createTask;

	public StartTaskByNamePersistence(
			final StartTaskDelegate starttaskDelegate,
			final StartTaskByNameDelegate startTaskDelegate,
			final TasksView tasks, final CreateTaskDelegate createTask) {
		this.startTaskDelegate = starttaskDelegate;
		startTaskByNameDelegate = startTaskDelegate;
		this.tasks = tasks;
		this.createTask = createTask;
	}

	@Override
	public void start() {
		startTaskByNameDelegate
				.addListener(new Delegate.Listener<StartTaskData>() {
					@Override
					public void execute(final StartTaskData data) {
						startTaskByName(data);
					}
				});
	}

	private void startTaskByName(final StartTaskData data) {
		final NonEmptyString taskName = data.taskName();
		final Maybe<TaskView> task = tasks.byName(taskName);

		if (task == null) {
			createTaskOrCry(taskName);
			startTaskByName(data);
			return;
		}

		startTaskDelegate.starTask(new StartTaskData(taskName, data
				.millisecondsAgo()));

	}

	private void createTaskOrCry(final NonEmptyString name) {
		createTask.createTask(new TaskData(name, 0.0));

		final Maybe<TaskView> createdTask = tasks.byName(name);
		if (createdTask == null) {
			throw new IllegalStateException("Task " + name + " not created.");
		}
	}

	@Override
	public void stop() {

	}

}
