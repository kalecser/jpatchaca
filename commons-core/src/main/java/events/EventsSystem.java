package events;

import java.io.Serializable;

import org.picocontainer.Startable;

public interface EventsSystem extends Startable{

	public void writeEvent(Serializable event);
	public void addProcessor(Processor<?> processor);
	public void addEventHook(EventHook<? extends Serializable> hook);
	public int getEventCount();
	
}
