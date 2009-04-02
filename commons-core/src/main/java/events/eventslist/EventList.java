package events.eventslist;

import java.io.Serializable;

import events.Processor;

public interface EventList  {
	void add(Serializable elementAdded);
	void addProcessors(Processor<?>[] processor);
	int getEventCount();
}