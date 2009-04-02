package tasks;

import tasks.tasks.TaskView;

public interface TasksListener {

	void createdTask(TaskView task);
	void removedTask(TaskView task);
}
