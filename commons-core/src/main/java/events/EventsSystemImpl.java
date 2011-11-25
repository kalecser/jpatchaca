package events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import events.eventslist.EventList;

public class EventsSystemImpl implements EventsSystem {

	private boolean started;
	private final EventList eventList;
	private final List<Processor<?>> processors;
	private final List<EventHook<?>> eventHooks;
	

	public EventsSystemImpl(EventList eventList) {
		
		this.eventList = eventList;
		processors = new ArrayList<Processor<?>>();
		eventHooks = new ArrayList<EventHook<?>>();
	}

	@Override
	public synchronized void writeEvent(Serializable event) {
			eventList.add(event);
			fireHooks(event);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private synchronized void fireHooks(Serializable event) {
		for (final EventHook hook: eventHooks){
			if (hook.getEventType().isAssignableFrom(event.getClass())){
				hook.hookEvent(event);
			}
		}
	}

	@Override
	public synchronized void addProcessor(Processor<?> processor) {
		if (started)
			throw new RuntimeException("Can't add processors, system is already started");
		
		processors.add(processor);
		
	}

	@Override
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

	@Override
	public void stop() {
		
	}

	@Override
	public synchronized void addEventHook(EventHook<? extends Serializable> hook) {
		eventHooks.add(hook);
	}

	@Override
	public synchronized int getEventCount() {
		return eventList.getEventCount();
	}

	@Override
	public synchronized void consume(Serializable event) {
		writeEvent(event);
	}


}
