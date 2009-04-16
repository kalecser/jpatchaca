package tasks.persistence;

import java.io.Serializable;

import events.EventsConsumer;

public class MockEventsConsumer implements EventsConsumer {

	private Serializable lastEvent = null;
	
	@Override
	public synchronized void consume(Serializable event) {
		lastEvent = event;		
	}
	
	public synchronized Serializable getEvent(){
		return lastEvent;	
	}

}
