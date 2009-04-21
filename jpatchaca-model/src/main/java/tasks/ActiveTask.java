package tasks;

import lang.Maybe;

import org.reactive.Source;

import tasks.tasks.Task;

public class ActiveTask extends Source<Maybe<Task>> {

	public ActiveTask() {
		super(null);
	}

}
