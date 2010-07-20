package tasks.tasks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lang.Maybe;
import tasks.Task;
import tasks.TaskView;
import tasks.taskName.TaskNames;
import basic.NonEmptyString;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public class Tasks implements TasksView, TaskNames {

	private final Map<ObjectIdentity, Task> tasksById;
	private final Map<Task, ObjectIdentity> idsByTask;
	private final Map<Task, TaskNameUpdater> taskNameUpdaterByTask;
	private final List<Task> tasksList;
	private final List<String> names;

	public Tasks() {
		this.tasksById = new LinkedHashMap<ObjectIdentity, Task>();
		this.idsByTask = new LinkedHashMap<Task, ObjectIdentity>();
		this.tasksList = new ArrayList<Task>();
		taskNameUpdaterByTask = new LinkedHashMap<Task, TaskNameUpdater>();
		this.names = new ArrayList<String>();
	}

	public synchronized void add(final ObjectIdentity taskId, final Task task)
			throws MustBeCalledInsideATransaction {
		this.tasksById.put(taskId, task);
		this.idsByTask.put(task, taskId);
		this.tasksList.add(task);
		taskNameUpdaterByTask.put(task, new TaskNameUpdater(task, names));
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
		taskNameUpdaterByTask.get(task).release();
		taskNameUpdaterByTask.remove(task);

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

	@Override
	public synchronized boolean containsName(final String name) {
		for (final TaskView task : tasksList) {
			if (task.name().equals(name)) {
				return true;
			}
		}

		return false;
	}

	

}
