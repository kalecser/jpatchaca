package tasks.persistence;

import org.picocontainer.Startable;

import tasks.processors.StartTaskProcessor2;
import events.EventsSystem;

public class StartTaskProcessor2Register implements Startable {

	public StartTaskProcessor2Register(final EventsSystem eventsSystem,
			final StartTaskProcessor2 processor) {
		eventsSystem.addProcessor(processor);
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

}
