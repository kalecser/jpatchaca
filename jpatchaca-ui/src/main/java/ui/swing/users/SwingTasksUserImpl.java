package ui.swing.users;

import javax.swing.JOptionPane;

import org.reactivebricks.pulses.Signal;

import tasks.TasksSystem;
import tasks.tasks.TaskView;
import ui.swing.mainScreen.TaskList;
import ui.swing.mainScreen.tasks.TaskExclusionScreen;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.utils.Whiteboard;
import basic.Formatter;
import basic.UserOperationCancelledException;

public class SwingTasksUserImpl implements SwingTasksUser {

	private final TaskList tasksList;
	
	private final TaskExclusionScreen exclusionScreen;

	private final SelectedTaskSource selectedTask;
	
	//bug: remove all references to tasklist from here!

	public SwingTasksUserImpl(TaskList list, TaskExclusionScreen exclusionScreen, Formatter formatter, TasksSystem tasksSystem, Whiteboard whiteboard, SelectedTaskSource selectedTask){
		
		
		this.tasksList = list;
		
		this.exclusionScreen = exclusionScreen;
		this.selectedTask = selectedTask;
	}
	
	
	public TaskView getSelectedTask() {
		return this.tasksList.selectedTask();
	}

	public boolean isTaskExclusionConfirmed() {
		
		final int answer = exclusionScreen.confirmExclusion();
		
		return answer == JOptionPane.YES_OPTION;
	}

	

	public TaskView getPeriodMovingTarget() {
		return tasksList.dropTargetTask();	
	}

	public boolean isPeriodExclusionConfirmed() {
		return JOptionPane.showConfirmDialog(null,
				"Do you realy want to remove the selected period?", 
				"Period Removal Confirmation",	JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}


	public String getTextForNote() throws UserOperationCancelledException {
		
		
		final String noteText = JOptionPane.showInputDialog("Enter a note");
		
		if (noteText == null)
			throw new UserOperationCancelledException();
		
		return noteText;
	}


	@Override
	public TaskView selectedTask() {
		return tasksList.selectedTask();
	}


	@Override
	public Signal<TaskView> selectedTaskSignal() {
		return selectedTask;
	}


}
