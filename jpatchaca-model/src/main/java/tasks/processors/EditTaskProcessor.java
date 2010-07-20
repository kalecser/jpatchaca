package tasks.processors;

import java.io.Serializable;

import tasks.home.TasksHome;
import basic.NonEmptyString;
import events.EditTaskEvent;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class EditTaskProcessor implements Processor<EditTaskEvent> {

	private final TasksHome tasksHome;

	public EditTaskProcessor(TasksHome tasksHome) {
		this.tasksHome = tasksHome;
	}

	public void execute(final EditTaskEvent eventObj)
			throws MustBeCalledInsideATransaction {
		tasksHome.editTask(eventObj.taskId(), new NonEmptyString(eventObj.newNameForTask()), eventObj
				.newBudgetForTask());
	}

	public Class<? extends Serializable> eventType() {
		return EditTaskEvent.class;
	}

}
