package tasks.tasks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lang.Maybe;


import basic.NonEmptyString;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public class Tasks implements TasksView {

	private final Map<ObjectIdentity, Task> tasksById;
	private final Map<Task, ObjectIdentity> idsByTask;
	private final List<Task> tasksList;
	private final List<String> names;

	public Tasks() {
		this.tasksById = new LinkedHashMap<ObjectIdentity, Task>();
		this.idsByTask = new LinkedHashMap<Task, ObjectIdentity>();
		this.tasksList = new ArrayList<Task>();
		this.names = new ArrayList<String>();
	}

	public synchronized void add(final ObjectIdentity taskId, final Task task)
			throws MustBeCalledInsideATransaction {

		for (final Task ctask : tasksList) {
			if (ctask.name().equals(task.name())) {
				task.setName(task.name() + "_new");
				add(taskId, task);
				return;
			}
		}

		this.tasksById.put(taskId, task);
		this.idsByTask.put(task, taskId);
		this.tasksList.add(task);
		this.names.add(task.name());
	}

	public synchronized Task get(final ObjectIdentity oid) {
		final Task task = this.tasksById.get(oid);
		if (task == null) {
			throw new IllegalArgumentException("There is no task with taskId: "
					+ oid);
		}

		return task;
	}

	public synchronized ObjectIdentity idOf(final TaskView task) {
		return this.idsByTask.get(task);
	}

	public synchronized void remove(final TaskView task) {
		this.tasksById.remove(idOf(task));
		this.idsByTask.remove(task);
		this.tasksList.remove(task);
		this.names.remove(task.name());

	}

	public synchronized List<TaskView> tasks() {
		return new ArrayList<TaskView>(tasksList);
	}

	public synchronized List<String> taskNames() {
		return names;
	}

	public synchronized Maybe<Task> byName(final NonEmptyString string) {
		for (final Task task : tasksList) {
			if (task.name().equals(string.unbox())) {
				return Maybe.wrap(task);
			}
		}

		return null;
	}

}
