package basic.tests;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import basic.AlertImpl;
import basic.Subscriber;


public class AlertTest {
	
	private final Mockery context = new JUnit4Mockery();
	
	@Test
	public void testListening() {
	
		final Subscriber subscriber = context.mock(Subscriber.class);		
		
		final AlertImpl alert = new AlertImpl();
		alert.subscribe(subscriber);		
		
		context.checking(new Expectations() {{
			atLeast(1).of(subscriber).fire();
		}});		
		alert.fire();
	}
	
	@Test
	public void testEachSubscriberWillOnlyBeNotifiedOnce() {
		
		final Subscriber subscriber = context.mock(Subscriber.class);		
		
		final AlertImpl alert = new AlertImpl();
		alert.subscribe(subscriber);
		alert.subscribe(subscriber);
		
		context.checking(new Expectations() {{
			exactly(1).of(subscriber).fire();
		}});		
		alert.fire();
		
	}
}
