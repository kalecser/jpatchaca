package tasks.tasks;

import java.util.List;

import tasks.TaskView;

import lang.Maybe;


import basic.NonEmptyString;
import core.ObjectIdentity;

public interface TasksView {

	public TaskView get(ObjectIdentity oid);

	public ObjectIdentity idOf(TaskView task);

	public Maybe<? extends TaskView> byName(NonEmptyString string);

	public List<TaskView> tasks();

	public List<String> taskNames();

}