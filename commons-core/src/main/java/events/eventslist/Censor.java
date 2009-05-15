package events.eventslist;

import core.events.eventslist.EventTransaction;

public interface Censor {

	boolean accept(EventTransaction transaction);

}
