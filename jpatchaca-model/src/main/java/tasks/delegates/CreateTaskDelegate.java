package tasks.delegates;

import tasks.tasks.TaskData;
import ui.swing.mainScreen.Delegate;

public class CreateTaskDelegate extends Delegate<TaskData>{

	public void createTask(TaskData taskData) {
		execute(taskData);
	}
	
}
