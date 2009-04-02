package basic.mock;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import basic.BasicSystem;
import basic.IdProvider;
import basic.SystemClock;
import basic.SystemClockImpl;
import core.ObjectIdentity;

public class MockBasicSystem implements BasicSystem{

	private static final AtomicInteger id= new AtomicInteger(1000);
	private final MutablePicoContainer container;
	private final MockHardwareClock mockHardwareClock;
	private String nextId;

	public MockBasicSystem(){
		mockHardwareClock = new MockHardwareClock();
		container = new PicoBuilder().withCaching().withHiddenImplementations().build();;
		container.addComponent(mockHardwareClock);
		
		
		container.addComponent(new IdProvider() {
		
		
			public ObjectIdentity provideId() {
				if (nextId == null)
					nextId = String.valueOf(id.addAndGet(1));
					
				ObjectIdentity id = new ObjectIdentity(nextId);
				nextId = null;
				return id;
				
			}
		});
				
		container.addComponent(SystemClockImpl.class);	
	}
	
	public SystemClock systemClock() {
		return container.getComponent(SystemClock.class);
	}
	
	public IdProvider idProvider() {
		return container.getComponent(IdProvider.class);
	}

	public void setHardwareClockTime(Date date) {
		(container.getComponent(MockHardwareClock.class)).setTime(date);		
	}

	public void setNextId(String taskId) {
		nextId = taskId;
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
