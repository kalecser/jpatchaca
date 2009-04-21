package tasks.persistence;

import org.picocontainer.Startable;

import tasks.processors.CreateAndStartTaskProcessor;
import events.EventsSystem;

public class CreateAndStartTaskRegister implements Startable {

	public CreateAndStartTaskRegister(final EventsSystem eventsSystem,
			final CreateAndStartTaskProcessor processor) {
		eventsSystem.addProcessor(processor);
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

}
