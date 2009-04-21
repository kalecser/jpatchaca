package tasks.persistence;

import org.picocontainer.Startable;

import tasks.processors.CreateTaskProcessor;
import events.EventsSystem;

public class CreateTaskProcessorRegister implements Startable {

	public CreateTaskProcessorRegister(final EventsSystem eventsSystem,
			final CreateTaskProcessor processor) {
		eventsSystem.addProcessor(processor);
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

}
