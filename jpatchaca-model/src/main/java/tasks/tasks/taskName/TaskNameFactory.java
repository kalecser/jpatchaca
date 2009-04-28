package tasks.tasks.taskName;

import java.util.LinkedHashSet;
import java.util.Set;

import basic.NonEmptyString;

public class TaskNameFactory {

	public Set<String> names = new LinkedHashSet<String>();

	public synchronized TaskName createTaskname(final String taskName) {

		if (taskName == null) {
			return createTaskname("null_named_task");
		}

		if (taskName.isEmpty()) {
			return createTaskname("empty_named_task");
		}

		if (names.contains(taskName)) {
			return createTaskname(taskName + "_new");
		}

		names.add(taskName);
		return new TaskName(new NonEmptyString(taskName));
	}

}
