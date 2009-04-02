package org.reactivebricks.pulses;

import java.util.ArrayList;
import java.util.List;

import org.reactivebricks.commons.lang.Maybe;


public class Source<T> implements Signal<T>, PulseListener {

	private Maybe<? extends List<Receiver<T>>> receivers;
	private Pulse<T> currentValue;
	
	public Source(T initialValue) {
		currentValue = new Pulse<T>(-1, initialValue);
	}

	private List<Receiver<T>> receivers(){
		if (receivers == null)
			receivers = Maybe.wrap((new ArrayList<Receiver<T>>()));
		return receivers.unbox();
	}
	
	@Override
	public synchronized Pulse<T> addReceiver(Receiver<T> receiver) {			
		receivers().add(receiver);
		receiver.receive(currentValue);
		return currentValue;
	}

	public synchronized void supply(T value) {
		Pulse<T> pulse = new Pulse<T>(1, value);
		
		for (Receiver<T> receiver : receivers()){
			receiver.receive(pulse);				
		}
		
		currentValue = pulse; 
	}

	//performance: execute pulse in threadpool?
	@Override
	public synchronized void pulse(int id) {
		
		 
		
	}

	@Override
	public synchronized void removeReceiver(Receiver<T> receiver) {
		receivers().remove(receiver);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currentValue == null) ? 0 : currentValue.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Source other = (Source) obj;
		if (currentValue == null) {
			if (other.currentValue != null)
				return false;
		} else if (!currentValue.equals(other.currentValue))
			return false;
		return true;
	}

	@Override
	public T currentValue() {
		return currentValue.value();
	}
	
	
}
