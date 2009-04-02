package ui.swing.users;

import java.awt.Window;

import org.reactivebricks.pulses.Signal;

import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import basic.UserOperationCancelledException;

public interface SwingTasksUser {

	TaskData getNewTaskData(Window owner) throws UserOperationCancelledException;
	TaskData getNewTaskData(TaskView taskView, Window owner) throws UserOperationCancelledException;
	TaskView getSelectedTask();
	Signal<TaskView> selectedTaskSignal();
	
	boolean isTaskExclusionConfirmed();
	boolean isPeriodExclusionConfirmed();
	TaskView getPeriodMovingTarget();
	String getTextForNote() throws UserOperationCancelledException;
	
	void createAndStartTask(long time, Window owner);
	void createTask(Window owner);
	void editSelectedTask(Window owner);
	TaskView selectedTask();
	
	
}
