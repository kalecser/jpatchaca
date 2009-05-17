package tasks;

import lang.Maybe;

import org.reactive.Source;


public class ActiveTask extends Source<Maybe<Task>> {

	public ActiveTask() {
		super(null);
	}

}
