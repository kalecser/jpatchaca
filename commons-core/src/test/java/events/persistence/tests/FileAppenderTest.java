package events.persistence.tests;

import junit.framework.TestCase;

import org.junit.Ignore;

import wheel.io.files.Directory;
import wheel.io.files.impl.tranzient.TransientDirectory;
import core.events.eventslist.EventTransaction;
import events.PersistenceManager;
import events.persistence.FileAppenderPersistence;
import events.persistence.JavaSerializer;
import events.persistence.XMLSerializer;

public final class FileAppenderTest extends TestCase {

	private Directory directory = new TransientDirectory();
	
	public void testStartFromScratchFileAppender(){
		PersistenceManager filePersistence = new FileAppenderPersistence(directory, new JavaSerializer());
		testStartFromScratch(filePersistence);
		
		assertTrue(directory.fileExists("timer.dat"));
	}
	
	@Ignore
	public void testStartFromScratchXMLAppender(){
		PersistenceManager filePersistence = new FileAppenderPersistence(directory, new XMLSerializer());
		testStartFromScratch(filePersistence);
		
		assertTrue(directory.fileExists("timer.xml"));
	}	
	
	public void testStartFromScratch(PersistenceManager persistence){
		EventTransaction fortyTwo = new EventTransaction(42l, "forty two");
		EventTransaction fortyThree = new EventTransaction(43l, "forty three");
		
		assertEquals(0, persistence.getEventsFromFile().size());
		
		persistence.writeEvent(fortyTwo);		
		assertEquals(fortyTwo, persistence.getEventsFromFile().get(0));
		assertEquals(fortyTwo, persistence.getEventTransactions().get(0));
		
		
		persistence.writeEvent(fortyThree);		
		assertEquals(fortyTwo, 
				persistence.getEventsFromFile().get(0));
		assertEquals(fortyThree, 
				persistence.getEventsFromFile().get(1));
		
		assertEquals(fortyThree, 
				persistence.getEventTransactions().get(1));
	}
	
}
