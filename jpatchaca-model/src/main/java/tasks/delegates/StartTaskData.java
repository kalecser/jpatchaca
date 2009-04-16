package tasks.delegates;

import basic.NonEmptyString;

public class StartTaskData {

	private final NonEmptyString taskName;
	private final int millisecondsAgo;


	public StartTaskData(NonEmptyString taskName, int millisecondsAgo) {
		this.taskName = taskName;
		this.millisecondsAgo = millisecondsAgo;
	}

	
	public NonEmptyString taskName() {
		return taskName;
	}


	public Integer millisecondsAgo() {
		return millisecondsAgo;
	}

}
