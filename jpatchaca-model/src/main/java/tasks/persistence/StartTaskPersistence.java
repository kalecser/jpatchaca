package tasks.persistence;

import org.picocontainer.Startable;
import org.reactivebricks.commons.lang.Maybe;

import basic.NonEmptyString;

import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TaskView;
import tasks.tasks.TasksView;
import ui.swing.mainScreen.Delegate;
import events.EventsSystem;
import events.StartTaskEvent;

public class StartTaskPersistence implements Startable{

	private final EventsSystem eventsSystem;
	private final StartTaskDelegate startTaskDelegate;
	private final TasksView tasks;

	public StartTaskPersistence(EventsSystem eventsSystem, StartTaskDelegate startTaskDelegate, TasksView tasks){
		this.eventsSystem = eventsSystem;
		this.startTaskDelegate = startTaskDelegate;
		this.tasks = tasks;		
	}
	
	@Override
	public void start() {
		startTaskDelegate.addListener(new Delegate.Listener<StartTaskData>() {
			@Override
			public void execute(StartTaskData object) {
				NonEmptyString taskName = object.taskName();
				Maybe<TaskView> byName = tasks.byName(taskName);
				if (byName == null)
					throw new IllegalStateException("Task " + taskName + " does not exist");
				
				eventsSystem.writeEvent(new StartTaskEvent(tasks.idOf(byName.unbox())));
				
			}
		});
	}

	@Override
	public void stop() {
		
	}

}
