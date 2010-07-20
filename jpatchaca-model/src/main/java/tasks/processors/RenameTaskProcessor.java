package tasks.processors;

import java.io.Serializable;

import tasks.home.TasksHome;
import basic.NonEmptyString;
import events.Processor;
import events.RenameTaskEvent;
import events.persistence.MustBeCalledInsideATransaction;

public class RenameTaskProcessor implements Processor<RenameTaskEvent> {

	private final TasksHome tasksHome;

	public RenameTaskProcessor(final TasksHome tasksHome) {
		this.tasksHome = tasksHome;
	}

	public void execute(final RenameTaskEvent eventObj)
			throws MustBeCalledInsideATransaction {
		tasksHome.editTask(eventObj.getTaskId(), new NonEmptyString(eventObj.getNewNameForTask()), null);
	}

	public Class<? extends Serializable> eventType() {
		return RenameTaskEvent.class;
	}
}
