package tasks.delegates;

import basic.Delegate;
import basic.Delegate.Listener;

public class StartTaskDelegate  {
	
	private Delegate<StartTaskData> subject;

	public StartTaskDelegate(){
		subject = new Delegate<StartTaskData>();
	}

	public void starTask(final StartTaskData startTaskData) {
		subject.execute(startTaskData);
	}

	public void addListener(Listener<StartTaskData> listener) {
		subject.addListener(listener);
	}

}
