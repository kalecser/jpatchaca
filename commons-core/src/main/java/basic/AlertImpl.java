package basic;

import java.util.ArrayList;
import java.util.List;



public class AlertImpl implements Alert {
	private List<Subscriber> subscribers;

	public AlertImpl() {
		this.subscribers = new ArrayList<Subscriber>();
	}
	
	public synchronized void subscribe(Subscriber subscriber) {
		this.subscribers.add(subscriber);	
	}

	public synchronized void fire() {
		
		List<Subscriber> clonedSubscribers = new ArrayList<Subscriber>(subscribers);
		
		for(final Subscriber subscriber : clonedSubscribers) {
			subscriber.fire();
		}
		
	}

	public synchronized void unsubscribe(Subscriber subscriber) {
		subscribers.remove(subscriber);
	}
	
	@Override
	public String toString() {
	
		return subscribers.toString();
	}
}
