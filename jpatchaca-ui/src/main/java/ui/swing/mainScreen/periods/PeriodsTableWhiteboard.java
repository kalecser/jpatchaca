package ui.swing.mainScreen.periods;

import org.apache.commons.lang.Validate;
import org.reactive.Signal;
import org.reactive.Source;

public class PeriodsTableWhiteboard {

	private final Source<String> _message = new Source<String>("");

	public void postMessage(final String message) {
		Validate.notNull(message);

		_message.supply(message);
	}

	public Signal<String> messageBoard() {
		return _message;
	}

}
