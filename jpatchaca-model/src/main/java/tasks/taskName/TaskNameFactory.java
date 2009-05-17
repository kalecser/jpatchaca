package tasks.taskName;

import basic.NonEmptyString;

public class TaskNameFactory {

	private final TaskNames names;

	public TaskNameFactory(final TaskNames names) {
		this.names = names;
	}

	public synchronized TaskName createTaskname(final String taskName) {

		if (taskName == null) {
			return createTaskname("null_named_task");
		}

		if (taskName.isEmpty()) {
			return createTaskname("empty_named_task");
		}

		if (names.containsName(taskName)) {
			return createTaskname(taskName + "_new");
		}

		return new TaskName(new NonEmptyString(taskName));
	}

}
