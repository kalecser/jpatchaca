package tasks;

import java.util.Comparator;

import tasks.tasks.TaskView;

public class TasksByNameComparator implements Comparator<TaskView> {

	@Override
	public int compare(final TaskView o1, final TaskView o2) {
		return o1.name().compareTo(o2.name());
	}

}
