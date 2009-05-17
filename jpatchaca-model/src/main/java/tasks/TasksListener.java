package tasks;


public interface TasksListener {

	void createdTask(TaskView task);
	void removedTask(TaskView task);
}
