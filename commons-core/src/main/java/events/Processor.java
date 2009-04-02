package events;

import java.io.Serializable;

import events.persistence.MustBeCalledInsideATransaction;

public interface Processor<EventType extends Serializable> {

	void execute(EventType eventObj) throws MustBeCalledInsideATransaction;
	Class<? extends Serializable> eventType();
}
