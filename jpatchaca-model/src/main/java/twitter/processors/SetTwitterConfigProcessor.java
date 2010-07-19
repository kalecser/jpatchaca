package twitter.processors;

import java.io.Serializable;

import org.picocontainer.Startable;

import twitter.TwitterOptions;
import twitter.events.SetTwitterConfig;
import events.EventsSystem;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class SetTwitterConfigProcessor implements Processor<SetTwitterConfig>,
		Startable {

	private final TwitterOptions twitterOptions;

	public SetTwitterConfigProcessor(final TwitterOptions twitterHome,
			final EventsSystem eventsSystem) {
		this.twitterOptions = twitterHome;
		eventsSystem.addProcessor(this);
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return SetTwitterConfig.class;
	}

	@Override
	public void execute(final SetTwitterConfig eventObj)
			throws MustBeCalledInsideATransaction {
		twitterOptions.configure(eventObj.isTwitterLoggingEnabled(), eventObj
				.userName(), eventObj.password());
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

}
