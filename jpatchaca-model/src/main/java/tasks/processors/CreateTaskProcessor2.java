package tasks.processors;

import java.io.Serializable;

import tasks.tasks.TasksHome;
import events.CreateTaskEvent2;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class CreateTaskProcessor2 implements Processor<CreateTaskEvent2> {



	private final TasksHome tasksHome;

	public CreateTaskProcessor2(TasksHome tasksHome) {
		this.tasksHome = tasksHome;
	}

	public void execute(CreateTaskEvent2 event) throws MustBeCalledInsideATransaction {
		this.tasksHome.createTask(event.getObjectIdentity(), event.getTaskName(), event.getBudget());
	}

	public Class<? extends Serializable> eventType() {
		return CreateTaskEvent2.class;
	}

}
