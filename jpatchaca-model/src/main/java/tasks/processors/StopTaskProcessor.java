package tasks.processors;

import java.io.Serializable;

import tasks.tasks.TasksHome;
import events.Processor;
import events.StopTaskEvent;
import events.persistence.MustBeCalledInsideATransaction;

public class StopTaskProcessor implements Processor<StopTaskEvent> {

	private final TasksHome home;

	public StopTaskProcessor(TasksHome home) {
		this.home = home;
	}

	public void execute(StopTaskEvent event) throws MustBeCalledInsideATransaction {
		this.home.stop(event.getTaskId());
	}

	public Class<? extends Serializable> eventType() {
		return StopTaskEvent.class;
	}

}
