package basic.durable;

import java.util.Date;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import wheel.io.files.Directory;
import basic.BasicSystem;
import basic.FormatterImpl;
import basic.HardwareClock;
import basic.IdProvider;
import basic.SystemClock;
import basic.SystemClockImpl;

public class DurableBasicSystem implements BasicSystem{

	
	private final MutablePicoContainer container;

	public DurableBasicSystem(HardwareClock hardwareClock, Directory directory){
		
		if (hardwareClock == null)
			throw new IllegalArgumentException("hardwareClock must not be null");
		
		container = new PicoBuilder().withCaching().withHiddenImplementations().build();
		container.addComponent(new DoubleIdProvider(directory.getPath()));
		container.addComponent(hardwareClock);	
		container .addComponent(SystemClockImpl.class);
		container.addComponent(FormatterImpl.class);
		
	}

	private HardwareClock realClock() {
		return container.getComponent(HardwareClock.class);
	}

	public SystemClock systemClock() {
		return container.getComponent(SystemClock.class);
	}

	public IdProvider idProvider() {
		return container.getComponent(IdProvider.class);
	}
	
	public Date getHardwareTime() {
		return realClock().getTime();
	}

	@Override
	public Date getTime() {
		return systemClock().getDate();
	}

}
