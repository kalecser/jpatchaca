package events.persistence;

import java.util.ArrayList;
import java.util.List;

import core.events.eventslist.EventTransaction;
import events.PersistenceManager;

public class TransientPersistenceManager implements PersistenceManager {

	private ArrayList<EventTransaction> transactions;

	@Override
	public synchronized List<EventTransaction> getEventTransactions() {
		transactions = new ArrayList<EventTransaction>();
		return transactions;
	}

	@Override
	public synchronized void writeEvent(EventTransaction event) {
		transactions.add(event);
	}

	@Override
	public List<EventTransaction> getEventsFromFile() {
		return getEventTransactions();
	}
}
