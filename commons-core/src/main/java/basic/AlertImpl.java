package basic;

import java.util.ArrayList;
import java.util.List;



public class AlertImpl implements Alert {
	private final List<Subscriber> subscribers;

	public AlertImpl() {
		this.subscribers = new ArrayList<Subscriber>();
	}
	
	public synchronized void subscribe(Subscriber subscriber) {
		this.subscribers.add(subscriber);	
	}

	public synchronized void fire() {
		for(final Subscriber subscriber : this.subscribers) {
			subscriber.fire();
		}
	}

	public synchronized void unsubscribe(Subscriber subscriber) {
		subscribers.remove(subscriber);
	}
}
