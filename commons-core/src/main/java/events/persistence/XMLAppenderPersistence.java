package events.persistence;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import wheel.io.files.Directory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;

import core.events.eventslist.EventTransaction;

public class XMLAppenderPersistence extends FileAppenderPersistence{


	public XMLAppenderPersistence(Directory directory) {
		super(directory);
		fileName = "timer.xml";
	}
	
	@Override
	protected void writeObjectOrCry(EventTransaction event, OutputStream out) {
		
		try {
			ObjectOutputStream oos = new XStream().createObjectOutputStream(new OutputStreamWriter(out));
			oos.writeObject(event);
			oos.close();
		} catch (Throwable e) {
			throw new IllegalStateException("Error writing transaction", e);
		}
	}
	
	@Override
	protected Object readObjectOrCry(InputStream in) {
		try {
			ObjectInputStream ois = new XStream().createObjectInputStream(new InputStreamReader( in));
			return ois.readObject();
		}
		catch (StreamException e) {
			if(e.getMessage().equals(" : input contained no data")){
				return null;
			}
			throw new IllegalStateException("Error reading transaction", e);
		}
		catch (Throwable e) {
			throw new IllegalStateException("Error reading transaction", e);
		}
	}

}
