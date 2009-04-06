package ui.swing.users;

import org.reactivebricks.pulses.Signal;

import tasks.tasks.TaskView;
import basic.UserOperationCancelledException;

public interface SwingTasksUser {

	TaskView getSelectedTask();
	Signal<TaskView> selectedTaskSignal();
	
	boolean isTaskExclusionConfirmed();
	boolean isPeriodExclusionConfirmed();
	TaskView getPeriodMovingTarget();
	String getTextForNote() throws UserOperationCancelledException;
	
	TaskView selectedTask();
	
	
}
