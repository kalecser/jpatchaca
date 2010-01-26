package tasks.persistence;

import org.picocontainer.Startable;

import tasks.processors.StartTaskProcessor;
import tasks.processors.StartTaskProcessor2;
import tasks.processors.StartTaskProcessor3;
import events.EventsSystem;

public class StartTaskProcessorRegister implements Startable {

	public StartTaskProcessorRegister(final EventsSystem eventsSystem,
			final StartTaskProcessor processor,
			final StartTaskProcessor2 processor2,
			final StartTaskProcessor3 processor3) {
		eventsSystem.addProcessor(processor);
		eventsSystem.addProcessor(processor2);
		eventsSystem.addProcessor(processor3);
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

}
