package tasks;

import java.text.Collator;
import java.util.Comparator;


public class TasksByNameComparator implements Comparator<TaskView> {

	private final Collator collator;

	public TasksByNameComparator() {
		collator = Collator.getInstance();
		collator.setStrength(Collator.PRIMARY);
	}

	@Override
	public synchronized int compare(final TaskView o1, final TaskView o2) {
		return collator.compare(o1.name(), o2.name());
	}

}
