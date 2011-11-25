package tasks.processors;

import java.io.Serializable;
import java.util.Date;

import periods.Period;
import tasks.TaskView;
import tasks.home.TasksHome;
import tasks.tasks.TasksView;
import core.ObjectIdentity;
import events.EditPeriodEvent2;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class EditPeriodProcessor2 implements Processor<EditPeriodEvent2> {

	private final TasksHome tasksHome;
	private final TasksView tasks;

	public EditPeriodProcessor2(final TasksHome tasksHome, final TasksView tasks) {
		this.tasks = tasks;
		this.tasksHome = tasksHome;
	}

	@Override
	public void execute(final EditPeriodEvent2 event)
			throws MustBeCalledInsideATransaction {
		final ObjectIdentity taskId = event.getTaskId();
		final TaskView task = this.tasks.get(taskId);
		final Period period = task.periods().get(event.getSelectedPeriod());

		final Date newStopTime = event.getStop();
		if (period.endTime() == null && newStopTime != null) {
			tasksHome.stop(taskId);
		}

		period.setStart(event.getStart());
		period.setStop(event.getStop());

	}

	@Override
	public Class<? extends Serializable> eventType() {
		return EditPeriodEvent2.class;
	}

}
