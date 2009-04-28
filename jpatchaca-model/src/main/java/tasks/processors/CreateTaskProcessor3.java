package tasks.processors;

import java.io.Serializable;

import tasks.tasks.TasksHome;
import tasks.tasks.taskName.TaskNameFactory;
import core.ObjectIdentity;
import events.CreateTaskEvent3;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class CreateTaskProcessor3 implements Processor<CreateTaskEvent3> {

	private final TasksHome tasksHome;
	private final TaskNameFactory taskNameFactory;

	public CreateTaskProcessor3(final TasksHome tasksHome,
			final TaskNameFactory taskNameFactory) {
		this.tasksHome = tasksHome;
		this.taskNameFactory = taskNameFactory;
	}

	public Class<? extends Serializable> eventType() {
		return CreateTaskEvent3.class;
	}

	@Override
	public void execute(final CreateTaskEvent3 eventObj)
			throws MustBeCalledInsideATransaction {
		final ObjectIdentity objectIdentity = eventObj.getObjectIdentity();
		tasksHome.createTask(objectIdentity, taskNameFactory
				.createTaskname(eventObj.getTaskName()), eventObj.getBudget());

	}

}
