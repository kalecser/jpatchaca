package tasks.processors;

import java.io.Serializable;

import tasks.home.TasksHome;
import tasks.taskName.TaskNameFactory;
import events.CreateTaskEvent;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class CreateTaskProcessor implements Processor<CreateTaskEvent> {

	private final TasksHome tasksHome;
	private final TaskNameFactory taskNameFactory;

	public CreateTaskProcessor(final TasksHome tasksHome,
			final TaskNameFactory taskNameFactory) {
		this.tasksHome = tasksHome;
		this.taskNameFactory = taskNameFactory;
	}

	@Override
	public void execute(final CreateTaskEvent event)
			throws MustBeCalledInsideATransaction {
		this.tasksHome.createTask(event.getIdentity(), taskNameFactory
				.createTaskname(event.getTaskName()), null);
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return CreateTaskEvent.class;
	}

}
