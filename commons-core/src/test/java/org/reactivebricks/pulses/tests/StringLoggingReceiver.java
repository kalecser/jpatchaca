package org.reactivebricks.pulses.tests;

import org.reactivebricks.pulses.Pulse;
import org.reactivebricks.pulses.Receiver;

public class StringLoggingReceiver implements Receiver<String> {

	private StringBuffer log = new StringBuffer();

	public String getLog() {
		return log.toString();
	}

	
	@Override
	public void receive(Pulse<String> pulse) {
		log.append(pulse.value() + " at " + pulse.id());		
	}

	public void clear() {
		log.delete(0, log.length());		
	}

	

}
