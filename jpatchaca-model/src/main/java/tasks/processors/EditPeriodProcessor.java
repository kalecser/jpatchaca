package tasks.processors;

import java.io.Serializable;

import periods.Period;
import tasks.TaskView;
import tasks.tasks.TasksView;
import core.ObjectIdentity;
import events.EditPeriodEvent;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class EditPeriodProcessor implements Processor<EditPeriodEvent> {

	private final TasksView tasks;


	public EditPeriodProcessor(TasksView tasks) {
		this.tasks = tasks;
	}


	public void execute(EditPeriodEvent event) throws MustBeCalledInsideATransaction {
		ObjectIdentity taskId = event.getTaskId();
		final TaskView task = this.tasks.get(taskId);
		final Period period = task.periods().get(event.getSelectedPeriod());
		
		period.setStart(event.getStart());
		period.setStop(event.getStop());
		
	}

	
	public Class<? extends Serializable> eventType() {
		return EditPeriodEvent.class;
	}

}
