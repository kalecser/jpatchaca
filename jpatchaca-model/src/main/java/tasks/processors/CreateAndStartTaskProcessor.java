package tasks.processors;

import core.ObjectIdentity;
import tasks.tasks.TasksHome;
import events.CreateAndStartTask;
import events.CreateTaskEvent3;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class CreateAndStartTaskProcessor implements Processor<CreateAndStartTask> {

	
	private final TasksHome tasksHome;

	public CreateAndStartTaskProcessor(TasksHome tasksHome){
		this.tasksHome = tasksHome;
	}
	
	@Override
	public Class<CreateAndStartTask> eventType() {
		return CreateAndStartTask.class;
	}

	@Override
	public void execute(CreateAndStartTask eventObj) throws MustBeCalledInsideATransaction {
		CreateTaskEvent3 createTaskEvent = eventObj.getCreateTaskEvent();
		ObjectIdentity objectIdentity = createTaskEvent.getObjectIdentity();
		tasksHome.createTask(objectIdentity, createTaskEvent.getTaskName(), createTaskEvent.getBudget());
		tasksHome.start(objectIdentity);
		
	}

}
