package ui.swing.mainScreen;

import ui.swing.mainScreen.tasks.TaskScreen;


public class TaskListModel {

	private final TaskScreen taskScreen;

	public TaskListModel(TaskScreen taskScreen){
		this.taskScreen = taskScreen;
		
	}
	
	public void createTask() {
		taskScreen.createTask();
	}

}
