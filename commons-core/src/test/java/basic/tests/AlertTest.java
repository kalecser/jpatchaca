package basic.tests;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import basic.AlertImpl;
import basic.Subscriber;


public class AlertTest extends MockObjectTestCase {
	
	public void testListening() {
	
		final Subscriber subscriber = mock(Subscriber.class);		
		
		final AlertImpl alert = new AlertImpl();
		alert.subscribe(subscriber);		
		
		checking(new Expectations() {{
			atLeast(1).of(subscriber).fire();
		}});		
		alert.fire();
	}
	
	public void testEachSubscriberWillOnlyBeNotifiedOnce() {
		
		final Subscriber subscriber = mock(Subscriber.class);		
		
		final AlertImpl alert = new AlertImpl();
		alert.subscribe(subscriber);
		alert.subscribe(subscriber);
		
		checking(new Expectations() {{
			exactly(1).of(subscriber).fire();
		}});		
		alert.fire();
		
	}
}
