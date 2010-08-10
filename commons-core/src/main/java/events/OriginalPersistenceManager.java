package events;

import java.util.List;

import core.events.eventslist.EventTransaction;

public interface OriginalPersistenceManager {
	void markAsMigrated();

	List<EventTransaction> getEventTransactions();

	List<EventTransaction> getEventsFromFile();

}
