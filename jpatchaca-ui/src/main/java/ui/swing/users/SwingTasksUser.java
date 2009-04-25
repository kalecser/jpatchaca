package ui.swing.users;

import tasks.tasks.TaskView;
import basic.UserOperationCancelledException;

public interface SwingTasksUser {

	TaskView getSelectedTask();

	boolean isTaskExclusionConfirmed();

	boolean isPeriodExclusionConfirmed();

	TaskView getPeriodMovingTarget();

	String getTextForNote() throws UserOperationCancelledException;
}
