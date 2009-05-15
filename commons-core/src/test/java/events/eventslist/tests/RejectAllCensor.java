package events.eventslist.tests;

import core.events.eventslist.EventTransaction;
import events.eventslist.Censor;

public class RejectAllCensor  implements Censor{

	@Override
	public boolean accept(EventTransaction transaction) {
		return false;
	}

}
