package ui.swing.mainScreen;

import ui.swing.mainScreen.tasks.TaskScreenController;


public class TaskListModel {

	private final TaskScreenController taskScreen;

	public TaskListModel(TaskScreenController taskScreen){
		this.taskScreen = taskScreen;
		
	}
	
	public void createTask() {
		taskScreen.createTask();
	}

}
