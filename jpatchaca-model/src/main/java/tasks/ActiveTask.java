package tasks;

import org.reactivebricks.commons.lang.Maybe;
import org.reactivebricks.pulses.Source;

import tasks.tasks.Task;

public class ActiveTask extends Source<Maybe<Task>> {

	public ActiveTask() {
		super(null);
	}

}
