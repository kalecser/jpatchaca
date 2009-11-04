package tasks.processors;

import tasks.home.TasksHome;
import tasks.taskName.TaskName;
import tasks.taskName.TaskNameFactory;
import tasks.tasks.TasksView;
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
	private final TaskNameFactory taskNameFactory;

	public CreateAndStartTaskProcessor(final TasksHome tasksHome,
			final StartTaskProcessor2 startTaskProcessor,
			final TasksView tasks, final TaskNameFactory taskNameFactory) {
		this.tasksHome = tasksHome;
		this.startTaskProcessor = startTaskProcessor;
		this.tasks = tasks;
		this.taskNameFactory = taskNameFactory;
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

		final TaskName taskname = taskNameFactory
				.createTaskname(createTaskEvent.getTaskName());

		tasksHome.createTask(objectIdentity, taskname, createTaskEvent
				.getBudget());

		final String name = tasks.get(createTaskEvent.getObjectIdentity())
				.name();
		if (name.equals("")) {
			return;
		}
		startTaskProcessor.execute(new StartTaskEvent2(new basic.NonEmptyString(
				name), 0));

	}

}
