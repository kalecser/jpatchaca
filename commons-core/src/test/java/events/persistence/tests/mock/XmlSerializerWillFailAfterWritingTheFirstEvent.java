package events.persistence.tests.mock;

import wheel.io.files.Directory;
import core.events.eventslist.EventTransaction;
import events.persistence.XmlPersistenceManager;

public class XmlSerializerWillFailAfterWritingTheFirstEvent extends XmlPersistenceManager {

	public XmlSerializerWillFailAfterWritingTheFirstEvent(Directory directory) {
		super(directory);
	}

	private EventTransaction event;

	@Override
	public void writeEvent(EventTransaction event) {
		if(this.event != null)
			throw new IllegalStateException("Premeditate error writing event");
		
		this.event = event;
		super.writeEvent(event);
		
		
	}

}
