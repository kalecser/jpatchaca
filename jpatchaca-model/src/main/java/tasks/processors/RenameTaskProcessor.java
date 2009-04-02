package tasks.processors;

import java.io.Serializable;

import tasks.tasks.TasksHome;
import events.Processor;
import events.RenameTaskEvent;
import events.persistence.MustBeCalledInsideATransaction;

public class RenameTaskProcessor implements Processor<RenameTaskEvent> {

	private final TasksHome tasksHome;

	public RenameTaskProcessor(TasksHome tasksHome) {
		this.tasksHome = tasksHome;
		
	}

	public void execute(RenameTaskEvent eventObj) throws MustBeCalledInsideATransaction {
		tasksHome.editTask(eventObj.getTaskId(), eventObj.getNewNameForTask(), null);
	}

	public Class<? extends Serializable> eventType() {
		return RenameTaskEvent.class;
	}
}
