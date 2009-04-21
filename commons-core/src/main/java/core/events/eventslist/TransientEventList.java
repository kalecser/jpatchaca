package core.events.eventslist;

import basic.SystemClockImpl;
import basic.mock.MockHardwareClock;
import events.eventslist.EventListImpl;
import events.persistence.TransientPersistenceManager;

public class TransientEventList extends EventListImpl {

	public TransientEventList(MockHardwareClock machineClock,
			SystemClockImpl systemClock) {
		super(new TransientPersistenceManager(), machineClock, systemClock);
	}

	

}
