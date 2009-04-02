package events;

import java.util.List;

import core.events.eventslist.EventTransaction;



public interface PersistenceManager {
	List<EventTransaction> getEventTransactions();
	void writeEvent(EventTransaction event);
}
