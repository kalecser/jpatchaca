package twitter.processors;

import java.io.Serializable;

import org.picocontainer.Startable;

import twitter.events.SetTwitterConfig;
import events.EventsSystem;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class SetTwitterConfigProcessor implements Processor<SetTwitterConfig>,
		Startable {

	public SetTwitterConfigProcessor(final EventsSystem eventsSystem) {
		eventsSystem.addProcessor(this);
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return SetTwitterConfig.class;
	}

	@Override
	public void execute(final SetTwitterConfig eventObj)
			throws MustBeCalledInsideATransaction {
		
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

}
