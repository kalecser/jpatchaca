package tasks.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import events.EventsConsumer;

public class MockEventsConsumer implements EventsConsumer {

	private Serializable lastEvent = null;
	private final List<Serializable> events = new ArrayList<Serializable>();

	@Override
	public synchronized void consume(final Serializable event) {
		events.add(event);
		lastEvent = event;
	}

	public synchronized Serializable lastEvent() {
		return lastEvent;
	}

	public List<Serializable> events() {
		return events;
	}

}
