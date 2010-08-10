package events.persistence;

import java.util.List;

import wheel.io.files.Directory;
import core.events.eventslist.EventTransaction;
import events.DestinationPersistenceManager;

public class XmlPersistenceManager implements DestinationPersistenceManager{

	private FileAppenderPersistence subject;
	private final Directory directory;
	private XMLSerializer serializer;

	public XmlPersistenceManager(Directory directory){
		this.directory = directory;
		serializer = new XMLSerializer();
		subject= new FileAppenderPersistence(directory, serializer);
	}
	
	@Override
	public List<EventTransaction> getEventTransactions() {
		
		return subject.getEventTransactions();
	}

	@Override
	public List<EventTransaction> getEventsFromFile() {
		return subject.getEventsFromFile();
	}

	@Override
	public void writeEvent(EventTransaction event) {
		subject.writeEvent(event);		
	}

	@Override
	public void clear() {
		
		if (!directory.fileExists(serializer.fileName())){
			return;
		}
		directory.renameOrCry(serializer.fileName(), serializer.fileName()+ ".old");
	}
}
