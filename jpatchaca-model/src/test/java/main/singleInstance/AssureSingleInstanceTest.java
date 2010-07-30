package main.singleInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import basic.Subscriber;

public class AssureSingleInstanceTest {

	@Test
	public void testTwoInstances() {
		subscribeToTryedToCreateAnotherInstance();
		tryToLaunchAgainAndAssertExceptionHasBeenThrown();
		waitForNotification();
	}
	
	AssureSingleInstance subject = new AssureSingleInstance();
	CountDownLatch tryedToCreateAnotherInstance = new CountDownLatch(1);
	
	@Before
	public void setup(){
		subject.start();
	}
	
	@After
	public void after(){
		subject.stop();
	}

	private void waitForNotification() {
		try {
			tryedToCreateAnotherInstance.await(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new IllegalStateException("Timeout waiting tryedToCreateAnotherInstance notification", e);
		}
	}

	private void subscribeToTryedToCreateAnotherInstance() {
		subject.subscribeTryedToCreateAnotherInstance(new Subscriber() {
			@Override
			public void fire() {
				tryedToCreateAnotherInstance.countDown();
			}
		});
	}

	private void tryToLaunchAgainAndAssertExceptionHasBeenThrown() {
		try{
			new AssureSingleInstance().start();
			Assert.fail("Should throw AlreadyRunningApplicationException");
		}catch (AlreadyRunningApplicationException ex){
			//expected
		}
	}
}
