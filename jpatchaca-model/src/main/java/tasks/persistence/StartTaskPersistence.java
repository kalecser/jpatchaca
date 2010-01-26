package tasks.persistence;

import org.picocontainer.Startable;

import tasks.delegates.CreateTaskDelegate;
import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;
import tasks.home.TaskData;
import tasks.tasks.Tasks;
import basic.Delegate;
import basic.NonEmptyString;
import events.EventsConsumer;
import events.StartTaskEvent3;

public class StartTaskPersistence implements Startable {

	private final StartTaskDelegate startTaskDelegate;
	private final EventsConsumer eventsConsumer;
	private final Tasks tasks;
	private final CreateTaskDelegate createTask;

	public StartTaskPersistence(final EventsConsumer eventsConsumer,
			final StartTaskDelegate startTaskDelegate, final Tasks tasks,
			final CreateTaskDelegate createTask) {
		this.eventsConsumer = eventsConsumer;
		this.startTaskDelegate = startTaskDelegate;
		this.tasks = tasks;
		this.createTask = createTask;
	}

	@Override
	public void start() {
		startTaskDelegate.addListener(new Delegate.Listener<StartTaskData>() {
			@Override
			public void execute(final StartTaskData object) {
				final NonEmptyString taskName = object.taskName();

				if (tasks.byName(taskName) == null) {
					createTask.createTask(new TaskData(taskName, 0.0));
				}

				eventsConsumer.consume(new StartTaskEvent3(taskName, object
						.millisecondsAgo()));

			}
		});
	}

	@Override
	public void stop() {

	}

}
