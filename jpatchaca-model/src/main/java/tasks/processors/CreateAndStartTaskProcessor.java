package tasks.processors;

import tasks.tasks.TasksHome;
import tasks.tasks.TasksView;
import basic.NonEmptyString;
import core.ObjectIdentity;
import events.CreateAndStartTask;
import events.CreateTaskEvent3;
import events.Processor;
import events.StartTaskEvent2;
import events.persistence.MustBeCalledInsideATransaction;

public class CreateAndStartTaskProcessor implements
		Processor<CreateAndStartTask> {

	private final TasksHome tasksHome;
	private final StartTaskProcessor2 startTaskProcessor;
	private final TasksView tasks;

	public CreateAndStartTaskProcessor(final TasksHome tasksHome,
			final StartTaskProcessor2 startTaskProcessor, final TasksView tasks) {
		this.tasksHome = tasksHome;
		this.startTaskProcessor = startTaskProcessor;
		this.tasks = tasks;
	}

	@Override
	public Class<CreateAndStartTask> eventType() {
		return CreateAndStartTask.class;
	}

	@Override
	public void execute(final CreateAndStartTask eventObj)
			throws MustBeCalledInsideATransaction {
		final CreateTaskEvent3 createTaskEvent = eventObj.getCreateTaskEvent();
		final ObjectIdentity objectIdentity = createTaskEvent
				.getObjectIdentity();

		tasksHome.createTask(objectIdentity, createTaskEvent.getTaskName(),
				createTaskEvent.getBudget());

		final String name = tasks.get(createTaskEvent.getObjectIdentity())
				.name();
		if (name.equals("")) {
			return;
		}
		startTaskProcessor.execute(new StartTaskEvent2(
				new NonEmptyString(name), 0));

	}

}
