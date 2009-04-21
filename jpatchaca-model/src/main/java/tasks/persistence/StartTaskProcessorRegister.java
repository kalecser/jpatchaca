package tasks.persistence;

import org.picocontainer.Startable;

import tasks.processors.StartTaskProcessor;
import events.EventsSystem;

public class StartTaskProcessorRegister implements Startable {

	public StartTaskProcessorRegister(final EventsSystem eventsSystem,
			final StartTaskProcessor processor) {
		eventsSystem.addProcessor(processor);
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

}
