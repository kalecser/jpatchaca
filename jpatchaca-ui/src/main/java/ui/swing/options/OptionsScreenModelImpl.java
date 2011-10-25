package ui.swing.options;

import jira.JiraOptions;
import jira.events.SetJiraConfig;
import keyboardRotation.KeyboardRotationOptions;
import lang.Maybe;
import events.EventsSystem;
import events.SetKeyboardRotationOptions;

public class OptionsScreenModelImpl implements OptionsScreenModel {
	
	private final EventsSystem eventsSystem;
	private final JiraOptions jiraOptions;
	private final KeyboardRotationOptions keyboardRotationOptions;

	public OptionsScreenModelImpl(final EventsSystem eventsSystem, final JiraOptions jiraOptions, KeyboardRotationOptions keyboardRotationOptions) {
		this.eventsSystem = eventsSystem;
		this.jiraOptions = jiraOptions;
		this.keyboardRotationOptions = keyboardRotationOptions;
	}

	@Override
	public void setJiraConfig(final String url, final String username,
			final String password, boolean issueStatusManagementEnabled) {
		eventsSystem.writeEvent(new SetJiraConfig(url, username, password, issueStatusManagementEnabled));
		
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
