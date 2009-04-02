package basic;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TransientSubscribersAlertImpl implements Alert {

	List<WeakReference<Subscriber>> subscribers = new ArrayList<WeakReference<Subscriber>>();
	
	@Override
	public void subscribe(Subscriber subscriber) {
		subscribers.add(new WeakReference<Subscriber>(subscriber));
	}
	
	public void fire(){
		for (WeakReference<Subscriber> subscriber : subscribers){
			Subscriber maybeSubscriber = subscriber.get();
			if (maybeSubscriber != null)
				maybeSubscriber.fire();
		}
	}

}
