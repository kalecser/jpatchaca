package events;

import java.io.Serializable;

public interface EventHook<EventType> {

	Class<? extends Serializable> getEventType();
	void hookEvent(EventType event);

}
