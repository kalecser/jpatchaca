package tasks.tasks;

import java.util.List;

import basic.Alert;
import core.ObjectIdentity;

public interface TasksHomeView {

	public abstract TaskView getTaskView(ObjectIdentity taskId);

	public abstract ObjectIdentity getIdOfTask(TaskView task);

	public abstract List<TaskView> tasks();

	public abstract Alert taskListChangedAlert();

	

}