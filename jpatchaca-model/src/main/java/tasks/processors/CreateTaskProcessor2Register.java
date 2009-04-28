package tasks.processors;

import org.picocontainer.Startable;

import events.EventsSystem;

public class CreateTaskProcessor2Register implements Startable {

	public CreateTaskProcessor2Register(final EventsSystem system,
			final CreateTaskProcessor2 processor) {
		system.addProcessor(processor);
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

}
