package tasks.delegates;

import basic.Delegate;
import tasks.home.TaskData;

public class CreateTaskDelegate extends Delegate<TaskData>{

	public void createTask(TaskData taskData) {
		execute(taskData);
	}
	
}
