package tasks.processors;

import java.io.Serializable;

import periods.Period;
import tasks.tasks.TaskView;
import tasks.tasks.TasksHome;
import core.ObjectIdentity;
import events.EditPeriodEvent;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class EditPeriodProcessor implements Processor<EditPeriodEvent> {

	private final TasksHome tasksHome;


	public EditPeriodProcessor(TasksHome tasksHome) {
		this.tasksHome = tasksHome;
	}


	public void execute(EditPeriodEvent event) throws MustBeCalledInsideATransaction {
		ObjectIdentity taskId = event.getTaskId();
		final TaskView task = this.tasksHome.getTaskView(taskId);
		final Period period = task.periods().get(event.getSelectedPeriod());
		
		period.setStart(event.getStart());
		period.setStop(event.getStop());
		
	}

	
	public Class<? extends Serializable> eventType() {
		return EditPeriodEvent.class;
	}

}
