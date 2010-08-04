package events.persistence.tests;

import junit.framework.TestCase;
import wheel.io.files.Directory;
import wheel.io.files.impl.tranzient.TransientDirectory;
import core.events.eventslist.EventTransaction;
import events.PersistenceManager;
import events.persistence.FileAppenderPersistence;
import events.persistence.XMLAppenderPersistence;

public final class FileAppenderTest extends TestCase {

	private Directory directory = new TransientDirectory();
	
	public void testStartFromScratchFileAppender(){
		PersistenceManager filePersistence = new FileAppenderPersistence(directory);
		testStartFromScratch(filePersistence);
	}
	
	public void testStartFromScratchXMLAppender(){
		PersistenceManager filePersistence = new XMLAppenderPersistence(directory);
		testStartFromScratch(filePersistence);
	}	
	
	public void testStartFromScratch(PersistenceManager persistence){
		EventTransaction fortyTwo = new EventTransaction(42l, "forty two");
		EventTransaction fortyThree = new EventTransaction(43l, "forty three");
		
		assertEquals(0, persistence.getEventTransactions().size());
		
		persistence.writeEvent(fortyTwo);
		
		assertEquals(fortyTwo, persistence.getEventTransactions().get(0));
		
		persistence.writeEvent(fortyThree);
		assertEquals(fortyTwo, 
				persistence.getEventTransactions().get(0));
		assertEquals(fortyThree, 
				persistence.getEventTransactions().get(1));
	}

}
