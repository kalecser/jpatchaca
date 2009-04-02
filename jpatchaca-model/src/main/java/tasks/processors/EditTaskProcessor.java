package tasks.processors;

import java.io.Serializable;

import tasks.tasks.TasksHome;
import events.EditTaskEvent;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class EditTaskProcessor implements Processor<EditTaskEvent> {

	private final TasksHome tasksHome;

	public EditTaskProcessor(TasksHome home) {
		this.tasksHome = home;		
	}
	
	public void execute(EditTaskEvent eventObj) throws MustBeCalledInsideATransaction {
		tasksHome.editTask(eventObj.taskId(), eventObj.newNameForTask(), eventObj.newBudgetForTask());
	}

	public Class<? extends Serializable> eventType() {
		return EditTaskEvent.class;
	}

}
