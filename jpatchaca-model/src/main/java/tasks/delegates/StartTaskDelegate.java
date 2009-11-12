package tasks.delegates;

import basic.Delegate;

public class StartTaskDelegate extends Delegate<StartTaskData> {

	public void starTask(final StartTaskData startTaskData) {
		super.execute(startTaskData);
	}

}
