package events.eventslist.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import basic.SystemClockImpl;
import basic.mock.MockHardwareClock;

import core.events.eventslist.EventTransaction;
import events.PersistenceManager;
import events.Processor;
import events.eventslist.EventListImpl;
import events.persistence.MustBeCalledInsideATransaction;

public class EventListTest extends MockObjectTestCase {

	private EventListImpl list;

	private PersistenceManagerMock persistenceManager;

	private MockHardwareClock mockHardwareClock;

	private SystemClockImpl systemClock;


	@Override
	public void setUp() {

		mockHardwareClock = new MockHardwareClock();
		systemClock = new SystemClockImpl();
		persistenceManager = new PersistenceManagerMock();

	}

	@SuppressWarnings("unchecked")
	public void testPersistence() throws MustBeCalledInsideATransaction {
		final String storedEvent = "previously on JPatchaca";
		final long storedEventTime = 1L;
		persistenceManager.writeEvent(new EventTransaction(storedEventTime,
				storedEvent));

		list = new EventListImpl(persistenceManager, mockHardwareClock, systemClock);

		final Processor<String> mockProcessor = mock(Processor.class);
		
		checking(new Expectations() {{
			one(mockProcessor).eventType(); will(returnValue(storedEvent.getClass()));
			one(mockProcessor).execute(storedEvent);
		}});
	
		list.addProcessors(new Processor[] { mockProcessor });
		assertTrue(storedEventTime == systemClock.getTime());

		final String newEvent = "2";
		final long newEventTime = 1000L;
		checking(new Expectations() {{
			one(mockProcessor).eventType(); will(returnValue(newEvent.getClass()));
			one(mockProcessor).execute(newEvent);
		}});
		mockHardwareClock.setTime(new Date(newEventTime));
		
		list.add(newEvent);
		assertEquals(newEvent, persistenceManager.getEventTransactions().get(1)
				.getEvent());
		assertEquals(newEventTime, (long)persistenceManager.getEventTransactions()
				.get(1).getTime());
		assertEquals(2, persistenceManager.getEventTransactions().size());
		assertTrue(newEventTime == systemClock.getTime());

	}

	@SuppressWarnings("unchecked")
	public void testProcessors() throws MustBeCalledInsideATransaction {
		final Double doubleEvent = new Double(10);
		final String stringEvent = "3";

		final Processor<String> stringProcessor = mock(Processor.class, "stringProcessor");
		final Processor<Double>doubleProcessor = mock(Processor.class, "doubleProcessor");

		list = new EventListImpl(persistenceManager, mockHardwareClock, systemClock);
		list.addProcessors(new Processor[] {
				stringProcessor,
				doubleProcessor});

		checking(new Expectations() {{
			atLeast(1).of(doubleProcessor).eventType(); will(returnValue(Double.class));
			atLeast(1).of(doubleProcessor).execute(doubleEvent);
			atLeast(1).of(stringProcessor).eventType(); will(returnValue(String.class));
			atLeast(1).of(stringProcessor).execute(stringEvent); 
		}});

		list.add(doubleEvent);
		list.add(stringEvent);
	}

	@SuppressWarnings("unchecked")
	public void testPersistenceAfterProcessor() throws MustBeCalledInsideATransaction {
		list = new EventListImpl(persistenceManager, mockHardwareClock, systemClock);

		
		
		final Processor<String> mockProcessor = mock(Processor.class);

		list = new EventListImpl(persistenceManager, mockHardwareClock, systemClock);
		list.addProcessors(new Processor[] { mockProcessor });

		checking(new Expectations() {{
			atLeast(1).of(mockProcessor).eventType(); will(returnValue(String.class));
			atLeast(1).of(mockProcessor).execute("3"); will(throwException(new RuntimeException("I've crached")));
		}});

		try {
			list.add("3");
			fail("It's suposed to raise an exception");
		} catch (final RuntimeException ex) {
		}

		assertEquals(0, persistenceManager.getEventTransactions().size());
	}

	@SuppressWarnings("unchecked")
	public void testThrowRuntimeIfNoProcessorForThatEventIsAvailable() {
		
		final Processor<Boolean> processorMock = mock(Processor.class);
		

		list = new EventListImpl(persistenceManager, mockHardwareClock, systemClock);
		list.addProcessors(new Processor[] { processorMock});

		checking(new Expectations() {{
			atLeast(1).of(processorMock).eventType(); will(returnValue(Boolean.class));
		}});

		try {
			list.add("string event, no processor can process me raraRaRaRARA");
			fail("Must raise exception because there are no processors for the event type String");
		} catch (final RuntimeException ex) {
		}// expected

	}
	
	public void testCensor(){
		list = new EventListImpl(persistenceManager, mockHardwareClock, systemClock, new RejectAllCensor());
		list.add("foo");
	}

	static class PersistenceManagerMock implements PersistenceManager {
		private final List<EventTransaction> eventsWritten = new ArrayList<EventTransaction>();

		public List<EventTransaction> getEventTransactions() {
			return eventsWritten;
		}

		public void writeEvent(final EventTransaction event) {
			eventsWritten.add(event);
		}

	}
	
	
}
