package ui.swing.mainScreen.tasks;

import tasks.TaskView;
import tasks.home.TaskData;

public interface TaskScreenModel {

	TaskView selectedTask();

	void createTask(TaskData data);

	void editTask(TaskView taskView, TaskData data);

}
