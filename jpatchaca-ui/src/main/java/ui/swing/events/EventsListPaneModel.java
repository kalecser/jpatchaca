package ui.swing.events;

import java.util.Date;

import basic.Formatter;
import core.events.eventslist.EventTransaction;
import events.persistence.FileAppenderPersistence;

public class EventsListPaneModel {

	private final FileAppenderPersistence persistence;
	private final Formatter formater;

	public EventsListPaneModel(final FileAppenderPersistence persistence,
			final Formatter formater) {
		this.persistence = persistence;
		this.formater = formater;
	}

	public synchronized String getEventsAsString() {
		final StringBuilder builder = new StringBuilder();
		for (final EventTransaction transaction : persistence
				.getEventTransactions()) {
			final Date date = new Date(transaction.getTime());
			builder.append(formater.formatShortDate(date) + " "
					+ formater.formatShortTime(date) + " - "
					+ transaction.getEvent().getClass().getSimpleName() + "\n");
		}
		return builder.toString();
	}

}
