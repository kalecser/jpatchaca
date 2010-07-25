package tasks.delegates;

import basic.Delegate;
import basic.Delegate.Listener;
import tasks.home.TaskData;

public class CreateTaskDelegate{

	private Delegate<TaskData> _subject;


	public CreateTaskDelegate(){
		_subject = new Delegate<TaskData>();
	}
	
	
	public void createTask(TaskData taskData) {
		_subject.execute(taskData);
	}


	public void addListener(Listener<TaskData> listener) {
		_subject.addListener(listener);		
	}
	
	
	
}
