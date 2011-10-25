package ui.swing.options;

import jira.JiraOptions;
import jira.events.SetJiraConfig;
import keyboardRotation.KeyboardRotationOptions;
import lang.Maybe;

import org.reactive.Signal;

import twitter.TwitterOptions;
import twitter.events.SetTwitterConfig;
import events.EventsSystem;
import events.SetKeyboardRotationOptions;

public class OptionsScreenModelImpl implements OptionsScreenModel {
	
	private final EventsSystem eventsSystem;
	private final TwitterOptions twitterOptions;
	private final JiraOptions jiraOptions;
	private final KeyboardRotationOptions keyboardRotationOptions;

	public OptionsScreenModelImpl(final EventsSystem eventsSystem,
			final TwitterOptions options, final JiraOptions jiraOptions, KeyboardRotationOptions keyboardRotationOptions) {
		this.eventsSystem = eventsSystem;
		this.twitterOptions = options;
		this.jiraOptions = jiraOptions;
		this.keyboardRotationOptions = keyboardRotationOptions;
	}

	public synchronized void setTwitterConfig(final boolean selected,
			final String username, final String password) {
		eventsSystem.writeEvent(new SetTwitterConfig(selected, username,
				password));

	}

	@Override
	public void setJiraConfig(final String url, final String username,
			final String password, boolean issueStatusManagementEnabled) {
		eventsSystem.writeEvent(new SetJiraConfig(url, username, password, issueStatusManagementEnabled));
		
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
	
	@Override
	public boolean isIssueStatusManagementEnabled() {
		return jiraOptions.isIssueStatusManagementEnabled();
	}

	@Override
	public void setKeyboarRotationConfig(boolean supressShakingDialog) {
			eventsSystem.writeEvent(new SetKeyboardRotationOptions(supressShakingDialog));
	}

	@Override
	public boolean supressShakingDialog() {
			return keyboardRotationOptions.supressShakingDialog();
	}

	@Override
	public void isToUseOldIcons() {
		throw new RuntimeException("Not implemented");
	}

}
