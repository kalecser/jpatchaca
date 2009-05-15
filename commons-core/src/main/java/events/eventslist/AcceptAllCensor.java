package events.eventslist;

import core.events.eventslist.EventTransaction;

public class AcceptAllCensor implements Censor{

	@Override
	public boolean accept(EventTransaction transaction) {
		return true;
	}

}
