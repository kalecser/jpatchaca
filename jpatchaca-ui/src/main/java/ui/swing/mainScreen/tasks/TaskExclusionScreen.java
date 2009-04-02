package ui.swing.mainScreen.tasks;

import javax.swing.JOptionPane;

import ui.swing.mainScreen.TaskList;

public class TaskExclusionScreen {

	private final TaskList tasksList;
	private final WindowManager manager;
	
	public TaskExclusionScreen(TaskList tasksList, WindowManager manager){
		this.tasksList = tasksList;
		this.manager = manager;
		
	}
	public int confirmExclusion() {
		
		
		return JOptionPane.showConfirmDialog(manager.getParentWindow(), 
				"Do you really want to delete task " + 
				this.tasksList.selectedTask().toString() + "?",
				"Task Removal Confirmation",
				JOptionPane.YES_NO_OPTION);
	}
	


}
