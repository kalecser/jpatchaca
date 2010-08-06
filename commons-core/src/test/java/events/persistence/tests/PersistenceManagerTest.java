package events.persistence.tests;

import junit.framework.TestCase;
import wheel.io.files.Directory;
import wheel.io.files.impl.tranzient.TransientDirectory;
import core.events.eventslist.EventTransaction;
import events.persistence.FileAppenderPersistence;
import events.persistence.JavaSerializer;

public final class PersistenceManagerTest extends TestCase {

	
	public void testStartFromScratch(){
		EventTransaction fortyTwo = new EventTransaction(42l, "forty two");
		EventTransaction fortyThree = new EventTransaction(43l, "forty three");
		
		Directory directory = new TransientDirectory();
		FileAppenderPersistence filePersistence = new FileAppenderPersistence(directory, new JavaSerializer());
		
		assertEquals(0, filePersistence.getEventTransactions().size());
		
		filePersistence.writeEvent(fortyTwo);
		assertEquals(fortyTwo, filePersistence.getEventsFromFile().get(0));
		
		filePersistence.writeEvent(fortyThree);
		assertEquals(fortyTwo, 
				filePersistence.getEventsFromFile().get(0));
		assertEquals(fortyThree, 
				filePersistence.getEventsFromFile().get(1));
	}
	
	
}
