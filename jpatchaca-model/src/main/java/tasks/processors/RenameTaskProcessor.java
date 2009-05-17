package tasks.processors;

import java.io.Serializable;

import tasks.home.TasksHome;
import tasks.taskName.TaskNameFactory;
import events.Processor;
import events.RenameTaskEvent;
import events.persistence.MustBeCalledInsideATransaction;

public class RenameTaskProcessor implements Processor<RenameTaskEvent> {

	private final TasksHome tasksHome;
	private final TaskNameFactory taskNameFactory;

	public RenameTaskProcessor(final TasksHome tasksHome,
			final TaskNameFactory taskNameFactory) {
		this.tasksHome = tasksHome;
		this.taskNameFactory = taskNameFactory;

	}

	public void execute(final RenameTaskEvent eventObj)
			throws MustBeCalledInsideATransaction {
		tasksHome.editTask(eventObj.getTaskId(), taskNameFactory
				.createTaskname(eventObj.getNewNameForTask()), null);
	}

	public Class<? extends Serializable> eventType() {
		return RenameTaskEvent.class;
	}
}
