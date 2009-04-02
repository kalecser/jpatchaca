package events.eventslist.tests;

import java.io.Serializable;

import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class BrokenProcessor implements Processor<String>{

	@Override
	public Class<? extends Serializable> eventType() {
		return String.class;
	}

	@Override
	public void execute(String eventObj) throws MustBeCalledInsideATransaction {
		throw new RuntimeException();		
	}

}
