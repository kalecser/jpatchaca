package org.reactivebricks.pulses;



public interface Signal<T> {

	Pulse<T> addReceiver(Receiver<T> receiver);
	void removeReceiver(Receiver<T> receiver);
	T currentValue();

}
