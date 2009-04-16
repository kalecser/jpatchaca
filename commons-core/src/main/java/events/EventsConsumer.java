package events;

import java.io.Serializable;

public interface EventsConsumer {

	void consume(Serializable event);

}
