package tasks.tasks;

import java.util.List;

import org.reactivebricks.commons.lang.Maybe;

import core.ObjectIdentity;

public interface TasksView {


	public TaskView get(ObjectIdentity oid);
	public ObjectIdentity idOf(TaskView task);
	public Maybe<TaskView> byName(String string);
	public List<TaskView> tasks();
	public List<String> taskNames();

}