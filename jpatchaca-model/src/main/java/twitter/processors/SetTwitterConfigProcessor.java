package twitter.processors;

import java.io.Serializable;

import org.picocontainer.Startable;

import twitter.TwitterOptions;
import twitter.events.SetTwitterConfig;
import events.EventsSystem;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class SetTwitterConfigProcessor implements Processor<SetTwitterConfig>, Startable {

	private final TwitterOptions twitterHome;

	public SetTwitterConfigProcessor(TwitterOptions twitterHome, EventsSystem eventsSystem){
		this.twitterHome = twitterHome;
		eventsSystem.addProcessor(this);
	}
	
	@Override
	public Class<? extends Serializable> eventType() {
		return SetTwitterConfig.class;
	}

	@Override
	public void execute(SetTwitterConfig eventObj)
			throws MustBeCalledInsideATransaction {
		twitterHome.configure(eventObj.isTwitterLoggingEnabled(), eventObj.userName(), eventObj.password());	
	}

	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		
	}

}
