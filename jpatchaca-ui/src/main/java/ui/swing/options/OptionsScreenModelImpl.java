package ui.swing.options;

import org.reactivebricks.pulses.Signal;

import twitter.TwitterOptions;
import twitter.events.SetTwitterConfig;
import events.EventsSystem;

public class OptionsScreenModelImpl implements OptionsScreenModel {
	
	private final EventsSystem eventsSystem;
	private final TwitterOptions options;

	public OptionsScreenModelImpl(EventsSystem eventsSystem, TwitterOptions options){
		this.eventsSystem = eventsSystem;
		this.options = options;
	}

	public synchronized void setConfig(boolean selected, String username, String password) {
		eventsSystem.writeEvent(new SetTwitterConfig(selected, username, password));
		
	}

	public Signal<Boolean> twitterEnabled() {
		return options.isTwitterLoggingEnabled();
	}

	public Signal<String> userName() {
		return options.username();
	}

	public Signal<String> password() {
		return options.password();
	}

}
