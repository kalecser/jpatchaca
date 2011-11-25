package events.tests;

import java.io.Serializable;

import static org.junit.Assert.*;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import basic.SystemClockImpl;
import basic.mock.MockHardwareClock;
import core.events.eventslist.TransientEventList;
import events.EventHook;
import events.EventsSystem;
import events.EventsSystemImpl;
import events.Processor;

public class EventsSystemTest {
	
	private final Mockery context = new JUnit4Mockery();
	
	private EventsSystem eventsSystem;
	
	@Before
	public void setUp() {
		eventsSystem = new EventsSystemImpl(new TransientEventList(new MockHardwareClock(), new SystemClockImpl()));
		
	}

	@Test
	public void testOnlyAddProcessorToNonStartedSystem(){
		eventsSystem.addProcessor(context.mock(Processor.class));
		eventsSystem.start();

		try{
			eventsSystem.addProcessor(context.mock(Processor.class, "processor2"));
			fail("Must raise...");
		} catch (final RuntimeException ex){
			assertEquals("Can't add processors, system is already started", ex.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCantAddSameProcessorTwice(){
		final Processor<Serializable> processor = context.mock(Processor.class);
		eventsSystem.addProcessor(processor);
		eventsSystem.addProcessor(processor);
		
		try{
			eventsSystem.start();
			fail("must throw exception");
		} catch (final RuntimeException ex){
			assertTrue(ex.getMessage().startsWith("You can't add the same processor twice"));
		}
		
	}

	@Test
	public void testEventHooks(){
		final StringBuilder log = new StringBuilder();
		
		eventsSystem.addProcessor(new Processor<String>() {
			
			@Override
			public Class<? extends Serializable> eventType() {
				return String.class;
			}
			
			@Override
			public void execute(final String eventObj) {
				log.append("executed\n");
			}
		});
		
		eventsSystem.addEventHook(new EventHook<String>(){
			
			@Override
			public void hookEvent(final String event){
				log.append("hooked - " + event);
			}

			@Override
			public Class<? extends Serializable> getEventType() {
				return String.class;
			}
		});
		
		eventsSystem.start();
		
		eventsSystem.writeEvent("test");
		assertEquals("executed\nhooked - test", log.toString());
	}
	
}
