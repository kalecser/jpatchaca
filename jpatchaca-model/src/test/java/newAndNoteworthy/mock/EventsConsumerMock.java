package newAndNoteworthy.mock;

import java.io.Serializable;

import tasks.MockEventsSystem;

import newAndNoteworthy.NewAndNoteworthyConsumptionProcessor;
import newAndNoteworthy.NewAndNoteworthyImpl;
import events.EventsConsumer;
import events.NewAndNoteworthyConsumed;
import events.persistence.MustBeCalledInsideATransaction;

public class EventsConsumerMock implements EventsConsumer{

	public NewAndNoteworthyImpl newAndNoteworthy;

	
	@Override
	public void consume(Serializable event) {
		try {
			new NewAndNoteworthyConsumptionProcessor(newAndNoteworthy, new MockEventsSystem()).execute((NewAndNoteworthyConsumed) event);
		} catch (MustBeCalledInsideATransaction e) {
			throw new RuntimeException("Not implemented");
		}
	}

}
