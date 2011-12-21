package tasks.delegates;

import tasks.home.TaskData;
import basic.Delegate.Listener;

public interface CreateTaskdelegate {

	public abstract void createTask(TaskData taskData);

	public abstract void addListener(Listener<TaskData> listener);

}