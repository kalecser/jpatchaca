package events.tests;

import java.io.Serializable;

import org.jmock.integration.junit3.MockObjectTestCase;

import wheel.io.files.impl.tranzient.TransientDirectory;
import basic.mock.MockBasicSystem;
import events.EventHook;
import events.EventsSystem;
import events.EventsSystemImpl;
import events.Processor;

public class EventsSystemTest extends MockObjectTestCase {
	
		
	
	private EventsSystem eventsSystem;
	private TransientDirectory transientDirectory;
	@Override
	protected void setUp() throws Exception {
		transientDirectory = new TransientDirectory();
		eventsSystem = new EventsSystemImpl(new MockBasicSystem(), transientDirectory);
		
	}

	public void testOnlyAddProcessorToNonStartedSystem(){
		eventsSystem.addProcessor(mock(Processor.class));
		eventsSystem.start();

		try{
			eventsSystem.addProcessor(mock(Processor.class, "processor2"));
			fail("Must raise...");
		}catch (final RuntimeException ex){
			assertEquals("Can't add processors, system is already started", ex.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void testCantAddSameProcessorTwice(){
		final Processor<Serializable> processor = mock(Processor.class);
		eventsSystem.addProcessor(processor);
		eventsSystem.addProcessor(processor);
		
		try{
			eventsSystem.start();
			fail("must throw exception");
		} catch (final RuntimeException ex){
			assertEquals("You can't add the same processor twice", ex.getMessage());
		}
		
	}

	
	public void testEventHooks(){
		final StringBuilder log = new StringBuilder();
		
		eventsSystem.addProcessor(new Processor<String>() {
			
			public Class<? extends Serializable> eventType() {
				return String.class;
			}
			
			public void execute(final String eventObj) {
				log.append("executed\n");
			}
		});
		
		eventsSystem.addEventHook(new EventHook<String>(){
			
			public void hookEvent(final String event){
				log.append("hooked - " + event);
			}

			public Class<? extends Serializable> getEventType() {
				return String.class;
			}
		});
		
		eventsSystem.start();
		
		eventsSystem.writeEvent("test");
		assertEquals("executed\nhooked - test", log.toString());
	}
	
}
