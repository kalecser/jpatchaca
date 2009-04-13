package tasks.persistence;

import org.picocontainer.Startable;

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
		startTaskDelegate.addListener(new Delegate.Listener<TaskView>() {
			@Override
			public void execute(TaskView object) {
				eventsSystem.writeEvent(new StartTaskEvent(tasks.idOf(object)));				
			}
		});
	}

	@Override
	public void stop() {
		
	}

}
