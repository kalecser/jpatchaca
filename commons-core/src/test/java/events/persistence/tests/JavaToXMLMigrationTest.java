package events.persistence.tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Ignore;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import wheel.io.files.impl.tranzient.TransientDirectory;
import core.events.eventslist.EventTransaction;
import events.DestinationPersistenceManager;
import events.PersistenceManager;
import events.persistence.FileAppenderPersistence;
import events.persistence.JavaPersistenceManager;
import events.persistence.JavaSerializer;
import events.persistence.PersistenceConverter;
import events.persistence.XmlPersistenceManager;
import events.persistence.tests.mock.XmlSerializerWillFailAfterWritingTheFirstEvent;

public class JavaToXMLMigrationTest extends TestCase {
	

	public void testMigration(){
		
		startSystemwithJavaSerializer();
		
		addEvent("foo");
		addEvent("bar");
		
		startSystemWithXmlSerializer();
		Assert.assertArrayEquals(new String[]{"foo", "bar"}, readEvents());
	}
	
	public void testMigrationOnlyHappensOnce(){
		
		startSystemwithJavaSerializer();
		
		addEvent("foo");
		addEvent("bar");
		
		startSystemWithXmlSerializer();
		addEvent("baz");
		
		startSystemWithXmlSerializer();
		Assert.assertArrayEquals(new String[]{"foo", "bar", "baz"}, readEvents());
	}
	
	@Ignore
	public void testMigrationInterruptedByFailure(){
		
		startSystemwithJavaSerializer();
		
		addEvent("foo");
		addEvent("bar");
		
		startSystemWithXmlSerializerWillFailAfterWritingTheFirstEvent();
		
		startSystemWithXmlSerializer();
		Assert.assertArrayEquals(new String[]{"foo", "bar"}, readEvents());
	}

	private PersistenceManager persistence;
	private TransientDirectory directory = new TransientDirectory();
	
	private void startSystemwithJavaSerializer() {
		persistence = new FileAppenderPersistence(directory, new JavaSerializer());		
	}
	
	private void addEvent(String stringEvent) {
		EventTransaction event = new EventTransaction((long)0,stringEvent);
		persistence.writeEvent(event);
	}
	
	private void startSystemWithXmlSerializer() {
		
		MutablePicoContainer container = new PicoBuilder().withLifecycle().withCaching().build();
		container.addComponent(directory);
		container.addComponent(JavaPersistenceManager.class);
		container.addComponent(XmlPersistenceManager.class);
		container.addComponent(PersistenceConverter.class);
		container.start();
		
		persistence = container.getComponent(DestinationPersistenceManager.class);
		
		
	}
	
	private String[] readEvents() {
		List<String> strings = new ArrayList<String>();
		for (EventTransaction tr: persistence.getEventsFromFile()){
			strings.add((String) tr.getEvent());
		}
		return strings.toArray(new String[]{});
	}

	private void startSystemWithXmlSerializerWillFailAfterWritingTheFirstEvent() {
		MutablePicoContainer container = new PicoBuilder().withLifecycle().withCaching().build();
		container.addComponent(directory);
		container.addComponent(JavaPersistenceManager.class);
		container.addComponent(XmlSerializerWillFailAfterWritingTheFirstEvent.class);
		container.addComponent(PersistenceConverter.class);
		
		try {
			container.start();			
			fail("Must throw exception");
		} catch (Exception e){
			assertEquals("Premeditate error writing event", e.getCause().getMessage());
		}
		
		persistence = container.getComponent(DestinationPersistenceManager.class);
		
	}


}
