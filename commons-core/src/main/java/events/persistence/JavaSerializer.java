package events.persistence;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import core.events.eventslist.EventTransaction;

public class JavaSerializer implements Serializer {

	@Override
	public Object readObjectOrCry(InputStream in) {
		Object readObject = null;
		
		try {
			readObject = new ObjectInputStream(in).readObject();
		}catch (EOFException e) {
			return null;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return readObject;
	}

	@Override
	public void writeObjectOrCry(EventTransaction event, OutputStream out) {
		try {
			new ObjectOutputStream(out).writeObject(event);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
