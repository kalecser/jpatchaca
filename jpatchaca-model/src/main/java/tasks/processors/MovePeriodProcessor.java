package tasks.processors;

import java.io.Serializable;

import tasks.home.TasksHome;
import events.MovePeriodEvent;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public final class MovePeriodProcessor implements Processor<MovePeriodEvent> {

	private final TasksHome tasksHome;

	public MovePeriodProcessor(TasksHome homeMock) {
		this.tasksHome = homeMock;
	}

	public void execute(MovePeriodEvent event) throws MustBeCalledInsideATransaction {
		tasksHome.transferPeriod(event.getSelectedTask(), event.getSelectedPeriod(), event.getTargetTask());
		
	}

	public Class<? extends Serializable> eventType() {
		return MovePeriodEvent.class;
	}

}
