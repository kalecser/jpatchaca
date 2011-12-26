package tasks.persistence;

import labels.labels.SelectedLabel;

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
	private final SelectedLabel selectedLabel;

	public StartTaskPersistence(final EventsConsumer eventsConsumer,
			final StartTaskDelegate startTaskDelegate, final Tasks tasks,
			final CreateTaskDelegate createTask, SelectedLabel selectedLabel) {
		this.eventsConsumer = eventsConsumer;
		this.startTaskDelegate = startTaskDelegate;
		this.tasks = tasks;
		this.createTask = createTask;
		this.selectedLabel = selectedLabel;
	}

	@Override
	public void start() {
		startTaskDelegate.addListener(new Delegate.Listener<StartTaskData>() {
			@Override
			public void execute(final StartTaskData object) {
				onStartTaskData(object);
			}
		});
	}

	@Override
	public void stop() {
		// Nothing to do.
	}

	void onStartTaskData(final StartTaskData object) {
		final TaskData taskdata = object.taskData();

		NonEmptyString taskName = new NonEmptyString(taskdata.getTaskName());
		if (tasks.byName(taskName) == null) {
			taskdata.setBudget(null);
			taskdata.setLabel(selectedLabel.selectedLabelCurrentValue());
			createTask.createTask(taskdata);
		}

		eventsConsumer.consume(new StartTaskEvent3(taskName, object
				.millisecondsAgo().longValue()));
	}

}
