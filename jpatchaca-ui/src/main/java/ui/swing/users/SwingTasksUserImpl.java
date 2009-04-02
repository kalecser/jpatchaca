package ui.swing.users;

import java.awt.Window;

import javax.swing.JOptionPane;

import org.reactivebricks.pulses.Signal;

import tasks.TasksSystem;
import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import ui.swing.mainScreen.TaskList;
import ui.swing.mainScreen.tasks.TaskExclusionScreen;
import ui.swing.mainScreen.tasks.TaskScreen;
import ui.swing.utils.Whiteboard;
import basic.Formatter;
import basic.UserOperationCancelledException;

public class SwingTasksUserImpl implements SwingTasksUser {

	private final TaskList tasksList;
	
	private final TaskExclusionScreen exclusionScreen;
	private final Formatter formatter;
	private final TasksSystem tasksSystem;
	
	private Whiteboard whiteboard;
	
	//bug: remove all references to tasklist from here!

	public SwingTasksUserImpl(TaskList list, TaskExclusionScreen exclusionScreen, Formatter formatter, TasksSystem tasksSystem, Whiteboard whiteboard){
		
		
		this.tasksList = list;
		
		this.exclusionScreen = exclusionScreen;
		this.formatter = formatter;
		this.tasksSystem = tasksSystem;
		this.whiteboard = whiteboard;	
	}
	
	
	public TaskData getNewTaskData(final TaskView taskView, Window owner) throws UserOperationCancelledException {
		
		final TaskScreen taskScreen = new TaskScreen(formatter, owner);
		
		taskScreen.setVisible( true, taskView);
		
		if (!taskScreen.okPressed()) throw new UserOperationCancelledException();
		
		return new TaskData(taskScreen.taskName(), taskScreen.taskBudget(), tasksList.selectedLabel());
	}
	
	public TaskData getNewTaskData(Window owner) throws UserOperationCancelledException {
		return getNewTaskData(null, owner);
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
	public void createAndStartTask(long time, Window owner) {
		
		try {
			tasksSystem.createAndStartTaskIn(getNewTaskData(owner), time * -1);
		} catch (UserOperationCancelledException e) {
			whiteboard.postMessage("Create task operation cancelled by user");
		}
	}


	@Override
	public void createTask(Window owner) {
		try {
			tasksSystem.createTask(getNewTaskData(owner));
		} catch (UserOperationCancelledException e) {
			whiteboard.postMessage("Create task operation cancelled by user");
		}
	}


	@Override
	public void editSelectedTask(Window owner) {
		TaskView selectedTask = getSelectedTask();
		try {
			tasksSystem.editTask(selectedTask, getNewTaskData(selectedTask, owner));
		} catch (UserOperationCancelledException e) {
			whiteboard.postMessage("Edit task operation cancelled by user");
		}
	}


	@Override
	public TaskView selectedTask() {
		return tasksList.selectedTask();
	}


	@Override
	public Signal<TaskView> selectedTaskSignal() {
		return tasksList.selectedTaskSignal();
	}


}
