package ui.swing.events;

import java.util.Date;

import basic.Formatter;
import core.events.eventslist.EventTransaction;
import events.PersistenceManager;

public class EventsListPaneModel {

	private final PersistenceManager persistence;
	private final Formatter formater;

	public EventsListPaneModel(final PersistenceManager persistence,
			final Formatter formater) {
		this.persistence = persistence;
		this.formater = formater;
	}

	public synchronized String getEventsAsString() {
		final StringBuilder builder = new StringBuilder();
		int i = 0;
		for (final EventTransaction transaction : persistence
				.getEventTransactions()) {
			final Date date = new Date(transaction.getTime());
			builder.append(i++ + " " + formater.formatShortDate(date) + " "
					+ formater.formatShortTime(date) + " - "
					+ transaction.getEvent().getClass().getSimpleName() + "\n");
		}
		return builder.toString();
	}

}
