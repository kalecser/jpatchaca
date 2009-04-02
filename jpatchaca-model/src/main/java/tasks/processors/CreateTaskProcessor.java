package tasks.processors;

import java.io.Serializable;

import tasks.tasks.TasksHome;
import events.CreateTaskEvent;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class CreateTaskProcessor implements Processor<CreateTaskEvent> {



	private final TasksHome tasksHome;

	public CreateTaskProcessor(TasksHome tasksHome) {
		this.tasksHome = tasksHome;
	}

	public void execute(CreateTaskEvent event) throws MustBeCalledInsideATransaction {
		this.tasksHome.createTask(event.getIdentity(), event.getTaskName(), null);
	}

	public Class<? extends Serializable> eventType() {
		return CreateTaskEvent.class;
	}

}
