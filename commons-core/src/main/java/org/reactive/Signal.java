package org.reactive;



public interface Signal<T> {

	T addReceiver(Receiver<T> receiver);
	void removeReceiver(Receiver<T> receiver);
	T currentValue();

}
