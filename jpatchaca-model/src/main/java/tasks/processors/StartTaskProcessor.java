package tasks.processors;

import java.io.Serializable;

import tasks.tasks.TasksHome;
import events.Processor;
import events.StartTaskEvent;
import events.persistence.MustBeCalledInsideATransaction;

public class StartTaskProcessor implements Processor<StartTaskEvent> {

	private final TasksHome home;

	public StartTaskProcessor(TasksHome home) {
		this.home = home;
	}

	public void execute(StartTaskEvent event) throws MustBeCalledInsideATransaction {	
		this.home.start(event.getTaskId());
	}

	public Class<? extends Serializable> eventType() {
		return StartTaskEvent.class;
	}

}
