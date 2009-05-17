package tasks.processors;

import java.io.Serializable;

import tasks.home.TasksHome;
import tasks.tasks.Tasks;
import events.Processor;
import events.RemoveTaskEvent;
import events.persistence.MustBeCalledInsideATransaction;

public class RemoveTaskProcessor implements Processor<RemoveTaskEvent> {

	private final Tasks tasks;
	private final TasksHome tasksHome;

	public RemoveTaskProcessor(Tasks tasks, TasksHome tasksHome) {
		this.tasks = tasks;
		this.tasksHome = tasksHome;
	}

	public void execute(RemoveTaskEvent eventObj) throws MustBeCalledInsideATransaction {
		this.tasksHome.remove(this.tasks.get(eventObj.getTaskId()));		
	}

	public Class<? extends Serializable> eventType() {
		return RemoveTaskEvent.class;
	}

}
