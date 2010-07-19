package ui.swing.options;

import jira.JiraOptions;
import jira.events.SetJiraConfig;
import lang.Maybe;

import org.reactive.Signal;

import twitter.TwitterOptions;
import twitter.events.SetTwitterConfig;
import events.EventsSystem;

public class OptionsScreenModelImpl implements OptionsScreenModel {
	
	private final EventsSystem eventsSystem;
	private final TwitterOptions twitterOptions;
	private final JiraOptions jiraOptions;

	public OptionsScreenModelImpl(final EventsSystem eventsSystem,
			final TwitterOptions options, final JiraOptions jiraOptions) {
		this.eventsSystem = eventsSystem;
		this.twitterOptions = options;
		this.jiraOptions = jiraOptions;
	}

	public synchronized void setTwitterConfig(final boolean selected,
			final String username, final String password) {
		eventsSystem.writeEvent(new SetTwitterConfig(selected, username,
				password));

	}

	@Override
	public void setJiraConfig(final String url, final String username,
			final String password) {
		eventsSystem.writeEvent(new SetJiraConfig(url, username, password));
		
	}

	public Signal<Boolean> twitterEnabled() {
		return twitterOptions.isTwitterLoggingEnabled();
	}

	public Signal<String> twitterUserName() {
		return twitterOptions.username();
	}

	public Signal<String> twitterPassword() {
		return twitterOptions.password();
	}

	@Override
	public Maybe<String> jiraUrl() {
		return jiraOptions.getURL();
	}

	@Override
	public Maybe<String> jiraUserName() {
		return jiraOptions.getUserName();
	}

	@Override
	public Maybe<String> jiraPassword() {
		return jiraOptions.getPassword();
	}

}
