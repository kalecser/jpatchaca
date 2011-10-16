package newAndNoteworthy;

import java.io.Serializable;

import org.picocontainer.Startable;

import events.EventsSystem;
import events.NewAndNoteworthyConsumed;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class NewAndNoteworthyConsumptionProcessor implements Processor<NewAndNoteworthyConsumed>, Startable{

	private final NewAndNoteworthyImpl newAndNoteworthy;

	public NewAndNoteworthyConsumptionProcessor(NewAndNoteworthyImpl newAndNoteworthy, EventsSystem eventsSystem){
		this.newAndNoteworthy = newAndNoteworthy;
		eventsSystem.addProcessor(this);
	}
	
	@Override
	public void execute(NewAndNoteworthyConsumed eventObj)
			throws MustBeCalledInsideATransaction {
		newAndNoteworthy.setLastConsumedTextHash(eventObj.hash);
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return NewAndNoteworthyConsumed.class;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

}
