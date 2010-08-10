package events.persistence;

import java.util.List;

import wheel.io.files.Directory;
import core.events.eventslist.EventTransaction;
import events.OriginalPersistenceManager;

public class JavaPersistenceManager implements OriginalPersistenceManager{
	
	private final  FileAppenderPersistence subject;
	private final JavaSerializer serializer;
	private final Directory directory;

	public JavaPersistenceManager(Directory directory){
		this.directory = directory;
		serializer = new JavaSerializer();
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
	public void markAsMigrated() {
		directory.renameOrCry(serializer.fileName(), serializer.fileName() + ".old");
	}
}
