package events.persistence.tests;

import junit.framework.TestCase;
import wheel.io.files.Directory;
import wheel.io.files.impl.tranzient.TransientDirectory;
import core.events.eventslist.EventTransaction;
import events.persistence.FileAppenderPersistence;
import events.persistence.SmartSerializerPersistenceManager;

public final class FileAppenderTest extends TestCase {

	
	public void testStartFromScratch(){
		EventTransaction fortyTwo = new EventTransaction(42l, "forty two");
		EventTransaction fortyThree = new EventTransaction(43l, "forty three");
		
		Directory directory = new TransientDirectory();
		FileAppenderPersistence filePersistence = new FileAppenderPersistence(directory);
		
		assertEquals(0, filePersistence.getEventTransactions().size());
		
		filePersistence.writeEvent(fortyTwo);
		assertEquals(fortyTwo, filePersistence.getEventTransactions().get(0));
		
		filePersistence.writeEvent(fortyThree);
		assertEquals(fortyTwo, 
				filePersistence.getEventTransactions().get(0));
		assertEquals(fortyThree, 
				filePersistence.getEventTransactions().get(1));
	}
	
	public void testMigration(){
		Directory directory = new TransientDirectory();
		
		EventTransaction fortyTwo = new EventTransaction(42l, "forty two");
		EventTransaction fortyThree = new EventTransaction(43l, "forty three");
		EventTransaction fortyFour = new EventTransaction(44l, "forty four");
		
		SmartSerializerPersistenceManager smart = new SmartSerializerPersistenceManager(directory);
		smart.writeEvent(fortyTwo);
		smart.writeEvent(fortyThree);
		
		FileAppenderPersistence filePersistence = new FileAppenderPersistence(directory);
		filePersistence.writeEvent(fortyFour);
		
		assertEquals(fortyTwo, filePersistence.getEventTransactions().get(0));
		assertEquals(fortyThree, filePersistence.getEventTransactions().get(1));
		assertEquals(fortyFour, filePersistence.getEventTransactions().get(2));
		
	}
	
}
