package tasks.processors;

import java.io.Serializable;

import tasks.home.TasksHome;
import tasks.taskName.TaskNameFactory;
import events.EditTaskEvent;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class EditTaskProcessor implements Processor<EditTaskEvent> {

	private final TasksHome tasksHome;
	private final TaskNameFactory taskNameFactory;

	public EditTaskProcessor(final TasksHome home,
			final TaskNameFactory taskNameFactory) {
		this.tasksHome = home;
		this.taskNameFactory = taskNameFactory;
	}

	public void execute(final EditTaskEvent eventObj)
			throws MustBeCalledInsideATransaction {
		tasksHome.editTask(eventObj.taskId(), taskNameFactory
				.createTaskname(eventObj.newNameForTask()), eventObj
				.newBudgetForTask());
	}

	public Class<? extends Serializable> eventType() {
		return EditTaskEvent.class;
	}

}
