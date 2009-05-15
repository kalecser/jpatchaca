package events.tests;

import junit.framework.TestCase;
import wheel.io.files.Directory;
import wheel.io.files.impl.tranzient.TransientDirectory;
import core.events.eventslist.EventTransaction;
import events.persistence.FileAppenderPersistence;

public final class FileAppenderTest extends TestCase {

	
	public void testStartFromScratch(){
		EventTransaction fortyTwo = new EventTransaction(42l, "forty two");
		EventTransaction fortyThree = new EventTransaction(43l, "forty three");
		
		Directory directory = new TransientDirectory();
		FileAppenderPersistence filePersistence = new FileAppenderPersistence(directory);
		
		assertEquals(0, filePersistence.getEventsFormFile().size());
		
		filePersistence.writeEvent(fortyTwo);
		assertEquals(fortyTwo, filePersistence.getEventsFormFile().get(0));
		
		filePersistence.writeEvent(fortyThree);
		assertEquals(fortyTwo, 
				filePersistence.getEventTransactions().get(0));
		assertEquals(fortyThree, 
				filePersistence.getEventTransactions().get(1));
	}
	
}
