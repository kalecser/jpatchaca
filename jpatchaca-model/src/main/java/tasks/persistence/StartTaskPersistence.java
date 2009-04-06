package tasks.persistence;

import org.picocontainer.Startable;

import tasks.TasksSystem;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TaskView;
import ui.swing.mainScreen.Delegate;

import events.EventsSystem;
import events.StartTaskEvent;

public class StartTaskPersistence implements Startable{

	private final EventsSystem eventsSystem;
	private final StartTaskDelegate startTaskDelegate;
	private final TasksSystem tasksSystem;

	public StartTaskPersistence(EventsSystem eventsSystem, StartTaskDelegate startTaskDelegate, TasksSystem tasksSystem){
		this.eventsSystem = eventsSystem;
		this.startTaskDelegate = startTaskDelegate;
		this.tasksSystem = tasksSystem;		
	}
	
	@Override
	public void start() {
		startTaskDelegate.addListener(new Delegate.Listener<TaskView>() {
			@Override
			public void execute(TaskView object) {
				eventsSystem.writeEvent(new StartTaskEvent(tasksSystem.getIdOfTask(object)));				
			}
		});
	}

	@Override
	public void stop() {
		
	}

}
