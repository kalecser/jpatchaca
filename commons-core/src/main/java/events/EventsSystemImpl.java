package events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import wheel.io.files.Directory;
import basic.BasicSystem;
import events.eventslist.EventList;
import events.eventslist.EventListImpl;
import events.persistence.FileAppenderPersistence;

public class EventsSystemImpl implements EventsSystem {

	private boolean started;
	private final MutablePicoContainer container;
	private final EventList eventList;
	private final List<Processor<?>> processors;
	private final List<EventHook<?>> eventHooks;
	

	public EventsSystemImpl(BasicSystem basicSystem, Directory directory) {
		container = new PicoBuilder().withCaching().withHiddenImplementations().build();
		container.addComponent(directory);
		container.addComponent(basicSystem);
		container.addComponent(EventListImpl.class);
		container.addComponent(FileAppenderPersistence.class);
		container.start();
		
		eventList = container.getComponent(EventList.class);
		processors = new ArrayList<Processor<?>>();
		eventHooks = new ArrayList<EventHook<?>>();
	}

	public synchronized void writeEvent(Serializable event) {
			eventList.add(event);
			fireHooks(event);
	}

	@SuppressWarnings("unchecked")
	private synchronized void fireHooks(Serializable event) {
		for (final EventHook hook: eventHooks){
			if (hook.getEventType().isAssignableFrom(event.getClass())){
				hook.hookEvent(event);
			}
		}
	}

	public synchronized void addProcessor(Processor<?> processor) {
		if (started)
			throw new RuntimeException("Can't add processors, system is already started");
		
		processors.add(processor);
		
	}

	public void start() {
		if (started)
			return;
		
		final Processor<?>[] processorsArray = new Processor[processors.size()];
		
		int i = 0;
		for (final Processor<?> processor : processors){
			processorsArray[i++] = processor;
		}
		eventList.addProcessors(processorsArray);
		started = true;
	}

	public void stop() {
		
	}

	public synchronized void addEventHook(EventHook<? extends Serializable> hook) {
		eventHooks.add(hook);
	}

	public synchronized int getEventCount() {
		return eventList.getEventCount();
	}

	@Override
	public synchronized void consume(Serializable event) {
		writeEvent(event);
	}


}
