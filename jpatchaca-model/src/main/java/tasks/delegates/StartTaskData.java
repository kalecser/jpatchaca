package tasks.delegates;

import tasks.home.TaskData;

public class StartTaskData {

	private final TaskData taskData;
	private final int millisecondsAgo;


	public StartTaskData(TaskData taskdata, int millisecondsAgo) {
		this.taskData = taskdata;
		this.millisecondsAgo = millisecondsAgo;
	}

	
	public TaskData taskData() {
		return taskData;
	}


	public Integer millisecondsAgo() {
		return millisecondsAgo;
	}

}
