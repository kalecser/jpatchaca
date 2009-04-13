package tasks.tasks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.reactivebricks.commons.lang.Maybe;

import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public class Tasks implements TasksView{

	private final Map<ObjectIdentity, Task> tasksById;
	private final Map<Task, ObjectIdentity> idsByTask;
	private final List<Task> tasksList;
	
	public Tasks(){
		this.tasksById = new LinkedHashMap<ObjectIdentity, Task>();
		this.idsByTask = new LinkedHashMap<Task, ObjectIdentity>();
		this.tasksList = new ArrayList<Task>();
	}
	
	public synchronized void add(ObjectIdentity taskId, Task task) throws MustBeCalledInsideATransaction{
		this.tasksById.put(taskId, task);
		this.idsByTask.put(task, taskId);
		this.tasksList.add(task);
	}

	public synchronized Task get(ObjectIdentity oid) {
		final Task task = this.tasksById.get(oid);
		if (task == null) throw new IllegalArgumentException("There is no task with taskId: " + oid);
		
		return task;
	}

	public synchronized ObjectIdentity idOf(TaskView task) {
		return this.idsByTask.get(task);
	}


	public synchronized void remove(TaskView task) {
		this.tasksById.remove(idOf(task));
		this.idsByTask.remove(task);
		this.tasksList.remove(task);
		
	}

	public synchronized List<TaskView> tasks() {
		return new ArrayList<TaskView>(tasksList);
	}

	public synchronized List<String> taskNames() {
		List<String> names = new ArrayList<String>();
		for (TaskView task : tasksList)
			names.add(task.name());
		
		return names;
	}
	
	public synchronized Maybe<TaskView> byName(String string) {
		for (TaskView task : tasksList)
			if (task.name().equals(string))
				return Maybe.wrap(task);
		
		return null;
	}
	
}
