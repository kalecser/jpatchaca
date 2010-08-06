package events.persistence;

import java.io.InputStream;
import java.io.OutputStream;

import core.events.eventslist.EventTransaction;

public interface Serializer {

	public abstract Object readObjectOrCry(InputStream in);

	public abstract void writeObjectOrCry(EventTransaction event,
			OutputStream out);

}