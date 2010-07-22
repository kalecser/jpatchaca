package main.singleInstance;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import basic.Subscriber;

public class AssureSingleInstanceTest {

	AssureSingleInstance subject = new AssureSingleInstance();
	protected boolean tryedToCreateAnotherInstance = false;
	
	@Before
	public void setup(){
		subject.start();
	}
	
	@Test
	public void testTwoInstances() {
		subject.subscribeTryedToCreateAnotherInstance(new Subscriber() {
			@Override
			public void fire() {
				tryedToCreateAnotherInstance = true;
			}
		});
		
		try{
			tryToLaunchAgain();
			Assert.fail("Should throw AlreadyRunningApplicationException");
		} catch (AlreadyRunningApplicationException ex){
			//expected
		}
		
		Assert.assertTrue(tryedToCreateAnotherInstance);
	}

	private void tryToLaunchAgain() {
		new AssureSingleInstance().start();
	}
	
	@Before
	public void after(){
		subject.stop();
	}
}
