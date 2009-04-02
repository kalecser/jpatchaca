package tasks.processors;

import java.io.Serializable;

import tasks.tasks.TasksHome;
import events.Processor;
import events.RemoveTaskEvent;
import events.persistence.MustBeCalledInsideATransaction;

public class RemoveTaskProcessor implements Processor<RemoveTaskEvent> {

	private final TasksHome tasksHome;

	public RemoveTaskProcessor(TasksHome tasksHome) {
		this.tasksHome = tasksHome;
	}

	public void execute(RemoveTaskEvent eventObj) throws MustBeCalledInsideATransaction {
		this.tasksHome.remove(this.tasksHome.getTaskView(eventObj.getTaskId()));		
	}

	public Class<? extends Serializable> eventType() {
		return RemoveTaskEvent.class;
	}

}
