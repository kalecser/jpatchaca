package tasks.processors;

import java.io.Serializable;

import periodsInTasks.PeriodsInTasksHome;
import tasks.tasks.TaskView;
import tasks.tasks.TasksHome;
import tasks.tasks.TasksHomeView;
import events.Processor;
import events.RemovePeriodEvent;
import events.persistence.MustBeCalledInsideATransaction;

public class RemovePeriodProcessor implements Processor<RemovePeriodEvent> {

	private final PeriodsInTasksHome periodsInTaskHome;
	private final TasksHomeView tasksHome;

	public RemovePeriodProcessor(final PeriodsInTasksHome periodsInTaskHome, final TasksHome tasksHome){
		this.periodsInTaskHome = periodsInTaskHome;
		this.tasksHome = tasksHome;		
	}
	
	public void execute(final RemovePeriodEvent eventObj) throws MustBeCalledInsideATransaction {
		
		final TaskView taskView = tasksHome.getTaskView(eventObj.getTaskId());
		periodsInTaskHome.removePeriodFromTask(taskView, taskView.getPeriod(eventObj.getPeriodIndex()));
	}

	public Class<? extends Serializable> eventType() {
		return RemovePeriodEvent.class;
	}

}
