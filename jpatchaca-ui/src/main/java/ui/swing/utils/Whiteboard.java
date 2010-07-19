package ui.swing.utils;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import lang.Maybe;

public class Whiteboard {

	private final Queue<String> warnings = new LinkedBlockingQueue<String>();

	public void postMessage(final String warning) {
		warnings.add(warning);
	}

	public Maybe<String> getMessage() {
		final String maybeMessage = warnings.poll();

		if (maybeMessage != null) {
			return Maybe.wrap(maybeMessage);
		}

		return null;
	}

}
