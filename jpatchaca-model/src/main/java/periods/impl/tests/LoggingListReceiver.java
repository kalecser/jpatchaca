package periods.impl.tests;

import org.apache.commons.lang.NotImplementedException;
import org.reactivebricks.pulses.Pulse;
import org.reactivebricks.pulses.Receiver;
import org.reactivebricks.pulses.list.ListChange;

public final class LoggingListReceiver implements Receiver<ListChange<Object>> {

	@Override
	public void receive(Pulse<ListChange<Object>> pulse) {
		throw new NotImplementedException();
		
	}

	

}
