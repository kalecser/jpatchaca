package org.reactivebricks.pulses.tests;

import org.reactive.Receiver;

public class StringLoggingReceiver implements Receiver<String> {

	private StringBuffer log = new StringBuffer();

	public String getLog() {
		return log.toString();
	}

	
	@Override
	public void receive(String pulse) {
		log.append(pulse);		
	}

	public void clear() {
		log.delete(0, log.length());		
	}

	

}
