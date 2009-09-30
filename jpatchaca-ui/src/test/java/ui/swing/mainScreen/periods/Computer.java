package ui.swing.mainScreen.periods;

import org.reactive.Signal;
import org.reactive.Source;

public class Computer {

	private final String name;
	private final int response;

	public Computer(final String name, final int response) {
		this.name = name;
		this.response = response;
	}

	public Signal<String> name() {
		return new Source<String>(name);
	}

	public Signal<Integer> responseTime() {
		return new Source<Integer>(response);
	}

}
