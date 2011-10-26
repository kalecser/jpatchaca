package basic;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;



public class AlertImpl implements Alert {
	private List<Subscriber> subscribers;

	public AlertImpl() {
		this.subscribers = new ArrayList<Subscriber>();
	}
	
	@Override
	public synchronized void subscribe(Subscriber subscriber) {
		this.subscribers.add(subscriber);	
	}

	public synchronized void fire() {
		
		Set<Subscriber> clonedSubscribers = new LinkedHashSet<Subscriber>(subscribers);
		
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
