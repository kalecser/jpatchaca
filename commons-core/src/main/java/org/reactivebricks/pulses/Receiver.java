package org.reactivebricks.pulses;


public interface Receiver<T> {

	public void receive(Pulse<T> pulse);
	
	
}
