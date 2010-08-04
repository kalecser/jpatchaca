package events.persistence;

import java.io.OutputStream;

import wheel.io.files.Directory;
import core.events.eventslist.EventTransaction;

public class XMLAppenderPersistence extends FileAppenderPersistence{

	public XMLAppenderPersistence(Directory directory) {
		super(directory);
	}
	
	@Override
	protected void writeObjectOrCry(EventTransaction event, OutputStream out) {
		super.writeObjectOrCry(event, out);
	}

}
