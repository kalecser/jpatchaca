package events.persistence;

import java.util.ArrayList;
import java.util.List;

import core.events.eventslist.EventTransaction;
import events.PersistenceManager;

public class TransientPersistenceManager implements PersistenceManager {

	@Override
	public List<EventTransaction> getEventTransactions() {
		return new ArrayList<EventTransaction>();
	}

	@Override
	public void writeEvent(EventTransaction event) {

	}

}
