package tasks;

import java.io.Serializable;

import events.EventHook;
import events.EventsSystem;
import events.Processor;

public class MockEventsSystem implements EventsSystem {

	@Override
	public void addEventHook(EventHook<? extends Serializable> hook) {

	}

	@Override
	public void addProcessor(Processor<?> processor) {

	}

	@Override
	public int getEventCount() {
		return 0;
	}

	@Override
	public void writeEvent(Serializable event) {

	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void consume(Serializable event) {
		
		
	}

}
