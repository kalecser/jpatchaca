package tasks.persistence;

import org.picocontainer.Startable;
import org.reactivebricks.commons.lang.Maybe;

import tasks.TasksSystem;
import tasks.delegates.StartTaskByNameDelegate;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import tasks.tasks.TasksView;
import ui.swing.mainScreen.Delegate;
import basic.NonEmptyString;

public class StartTaskByNamePersistence implements Startable{

	private final TasksView tasks;
	private final StartTaskDelegate startTaskDelegate;
	private final StartTaskByNameDelegate startTaskByNameDelegate;
	private final TasksSystem taskssSystem;

	public StartTaskByNamePersistence(StartTaskDelegate starttaskDelegate, StartTaskByNameDelegate startTaskDelegate, TasksView tasks, TasksSystem taskssSystem){
		this.startTaskDelegate = starttaskDelegate;
		startTaskByNameDelegate = startTaskDelegate;
		this.tasks = tasks;
		this.taskssSystem = taskssSystem;		
	}
	
	@Override
	public void start() {
		startTaskByNameDelegate.addListener(new Delegate.Listener<NonEmptyString>() {
			@Override
			public void execute(NonEmptyString name) {
				startTaskByName(name);
			}
		});
	}

	protected void startTaskByName(NonEmptyString name) {
		final Maybe<TaskView> task = tasks.byName(name.unbox());
		
		if (task == null){
			taskssSystem.createAndStartTaskIn(new TaskData(name, 0.0), 0);
			return;
		}
		
		startTaskDelegate.startTask(task.unbox());
	}

	@Override
	public void stop() {
		
	}

}
