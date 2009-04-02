package ui.swing.utils;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.reactivebricks.commons.lang.Maybe;

public class Whiteboard {

	private Queue<String> warnings = new LinkedBlockingQueue<String>();

	public void postMessage(String warning) {
		warnings.add(warning);		
	}
	
	public Maybe<String> getMessage(){
		String maybeMessage = warnings.poll();
		
		if (maybeMessage != null)
			return Maybe.wrap(maybeMessage);
		
		return null;
	}

}
