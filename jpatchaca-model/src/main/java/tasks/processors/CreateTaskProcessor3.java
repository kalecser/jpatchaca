package tasks.processors;

import java.io.Serializable;

import tasks.tasks.TasksHome;
import core.ObjectIdentity;
import events.CreateTaskEvent3;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class CreateTaskProcessor3 implements Processor<CreateTaskEvent3> {



	private final TasksHome tasksHome;

	public CreateTaskProcessor3(TasksHome tasksHome) {
		this.tasksHome = tasksHome;
	}

	public Class<? extends Serializable> eventType() {
		return CreateTaskEvent3.class;
	}

	@Override
	public void execute(CreateTaskEvent3 eventObj) throws MustBeCalledInsideATransaction {
		final ObjectIdentity objectIdentity = eventObj.getObjectIdentity();
		tasksHome.createTask(objectIdentity, eventObj.getTaskName(), eventObj.getBudget());
		
	}

}
