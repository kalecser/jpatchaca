package basic.mock;

import java.util.Date;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import basic.BasicSystem;
import basic.SystemClock;
import basic.SystemClockImpl;

public class MockBasicSystem implements BasicSystem{

	private final MutablePicoContainer container;
	private final MockHardwareClock mockHardwareClock;

	public MockBasicSystem(){
		mockHardwareClock = new MockHardwareClock();
		container = new PicoBuilder().withCaching().withHiddenImplementations().build();;
		container.addComponent(mockHardwareClock);				
		container.addComponent(SystemClockImpl.class);	
	}
	
	public SystemClock systemClock() {
		return container.getComponent(SystemClock.class);
	}

	public void setHardwareClockTime(Date date) {
		(container.getComponent(MockHardwareClock.class)).setTime(date);		
	}

	public Date getHardwareTime() {
		return mockHardwareClock.getTime();
	}

	public final MockHardwareClock getMockHardwareClock() {
		return mockHardwareClock;
	}
	
	@Override
	public Date getTime() {
		return systemClock().getDate();
	}
	

}
