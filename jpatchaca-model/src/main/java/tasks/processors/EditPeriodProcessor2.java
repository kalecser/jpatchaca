package tasks.processors;

import java.io.Serializable;
import java.util.Date;

import periods.Period;
import tasks.tasks.TaskView;
import tasks.tasks.TasksHome;
import tasks.tasks.TasksView;
import core.ObjectIdentity;
import events.EditPeriodEvent2;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class EditPeriodProcessor2 implements Processor<EditPeriodEvent2> {

	private final TasksHome tasksHome;
	private final TasksView tasks;


	public EditPeriodProcessor2(TasksHome tasksHome, TasksView tasks) {
		this.tasks = tasks;
		this.tasksHome = tasksHome;
	}


	public void execute(EditPeriodEvent2 event) throws MustBeCalledInsideATransaction {
		ObjectIdentity taskId = event.getTaskId();
		final TaskView task = this.tasks.get(taskId);
		final Period period = task.periods().get(event.getSelectedPeriod());
		
		Date newStopTime = event.getStop();
		if (period.endTime() == null && newStopTime != null)
			tasksHome.stop(taskId);
		
		period.setStart(event.getStart());
		period.setStop(event.getStop());
		
	}

	
	public Class<? extends Serializable> eventType() {
		return EditPeriodEvent2.class;
	}

}
