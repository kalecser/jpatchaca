package events.persistence;

import org.picocontainer.Startable;

import core.events.eventslist.EventTransaction;
import events.DestinationPersistenceManager;
import events.OriginalPersistenceManager;

public class PersistenceConverter implements Startable{

	private final DestinationPersistenceManager destination;
	private final OriginalPersistenceManager original;

	public PersistenceConverter(DestinationPersistenceManager destination, OriginalPersistenceManager original) {
		this.destination = destination;
		this.original = original;
	}

	@Override
	public void start() {
		
		if (original.getEventTransactions().size() == 0){
			return;
		}
		
		destination.clear();
		
		for (EventTransaction tr : original.getEventsFromFile()){
			destination.writeEvent(tr);
		}
		
		original.markAsMigrated();
	}

	@Override
	public void stop() {
		
	}

}
