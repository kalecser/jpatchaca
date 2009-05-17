package ui.swing.mainScreen.tasks;

import tasks.TaskView;
import tasks.home.TaskData;

public interface TaskScreenModel {

	TaskView selectedTask();

	void createTask(TaskData data);

	void createTaskAndStart(TaskData data, Long unbox);

	void editTask(TaskView taskView, TaskData data);

}
