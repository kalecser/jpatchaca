package tasks.persistence;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import events.EventsConsumer;

public class MockEventsConsumer implements EventsConsumer {

	private final LinkedList<Serializable> events = new LinkedList<Serializable>();

	@Override
	public synchronized void consume(final Serializable event) {
		events.add(event);
	}

	public synchronized Serializable popEvent() {
		return events.pop();
	}

	public List<Serializable> events() {
		return events;
	}

}
