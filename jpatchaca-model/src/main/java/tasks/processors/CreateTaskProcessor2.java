package tasks.processors;

import java.io.Serializable;

import tasks.tasks.TasksHome;
import tasks.tasks.taskName.TaskNameFactory;
import events.CreateTaskEvent2;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class CreateTaskProcessor2 implements Processor<CreateTaskEvent2> {

	private final TasksHome tasksHome;
	private final TaskNameFactory taskNameFactory;

	public CreateTaskProcessor2(final TasksHome tasksHome,
			final TaskNameFactory taskNameFactory) {
		this.tasksHome = tasksHome;
		this.taskNameFactory = taskNameFactory;
	}

	public void execute(final CreateTaskEvent2 event)
			throws MustBeCalledInsideATransaction {
		this.tasksHome.createTask(event.getObjectIdentity(), taskNameFactory
				.createTaskname(event.getTaskName()), event.getBudget());
	}

	public Class<? extends Serializable> eventType() {
		return CreateTaskEvent2.class;
	}

}
