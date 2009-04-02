package org.reactivebricks.pulses.tests;

import org.junit.Before;
import org.junit.Test;


public class SynchronizationClockTest {

	//private ManualPulseClock manualClock;
	//private ManualPulseClock manualClock1;
	//private SynchronizationClock synchronizationClock;
	//private Source<String> firstName;
	//private Source<String> lastName;

	@Before
	public void setUp(){
		//manualClock = new ManualPulseClock();
		//manualClock1 = new ManualPulseClock();
		
		//synchronizationClock = new SynchronizationClock();
		
		//firstName = new Source<String>(manualClock,"");
		//lastName = new Source<String>(manualClock1,"");
	}
	
	@Test
	public void testSynchronizationClock(){
		
//later
//		Signal<String> synchronizedFirstName = synchronizationClock.sync(firstName);
//		Signal<String> synchronizedLastName = synchronizationClock.sync(lastName);
//		
//		firstName.supply("Arthur");
//		lastName.supply("Dent");
//		
//		Assert.assertEquals(-1, synchronizationClock.id());
//		
//		manualClock.pulse();
//		Assert.assertEquals(new Pulse<String>(0, "Arthur"), firstName);
//		Assert.assertEquals(new Pulse<String>(-1, ""), lastName);
//		Assert.assertEquals(new Pulse<String>(-1, null), synchronizedFirstName);
//		Assert.assertEquals(new Pulse<String>(-1, null), synchronizedLastName);
//		Assert.assertEquals(-1, synchronizationClock.id());
//		
//		manualClock1.pulse();
//		Assert.assertEquals(new Pulse<String>(0, "Arthur"), firstName);
//		Assert.assertEquals(new Pulse<String>(0, "Dent"), lastName);
//		Assert.assertEquals(new Pulse<String>(0, "Arthur"), synchronizedFirstName);
//		Assert.assertEquals(new Pulse<String>(0, "Dent"), synchronizedLastName);
//		Assert.assertEquals(0, synchronizationClock.id());
		
	}
	
}
