package ui.swing.options;

import jira.JiraOptions;
import jira.events.SetJiraConfig;
import keyboardRotation.KeyboardRotationOptions;
import events.EventsSystem;
import events.SetKeyboardRotationOptions2;

public class OptionsScreenModelImpl implements OptionsScreenModel {

	private final EventsSystem eventsSystem;
	private final JiraOptions jiraOptions;
	private final KeyboardRotationOptions keyboardRotationOptions;

	public OptionsScreenModelImpl(final EventsSystem eventsSystem,
			final JiraOptions jiraOptions,
			KeyboardRotationOptions keyboardRotationOptions) {
		this.eventsSystem = eventsSystem;
		this.jiraOptions = jiraOptions;
		this.keyboardRotationOptions = keyboardRotationOptions;
	}

	@Override
	public Data readDataFromSystem() {
		Data data = new Data();
		data.jiraUrl = jiraOptions.getURL();
		data.jiraUserName = jiraOptions.getUserName();
		data.jiraPassword = jiraOptions.getPassword();
		data.issueStatusManagementEnabled = jiraOptions
				.isIssueStatusManagementEnabled();
		data.supressShakingDialog = keyboardRotationOptions
				.supressShakingDialog();
		data.isRemoteSystemIntegrationActive = keyboardRotationOptions.isRemoteIntegrationActive();
		return data;
	}

	@Override
	public void writeDataIntoSystem(Data data) {
		eventsSystem.writeEvent(new SetJiraConfig(data.jiraUrl.unbox(),
				data.jiraUserName.unbox(), data.jiraPassword.unbox(),
				data.issueStatusManagementEnabled));
		eventsSystem.writeEvent(new SetKeyboardRotationOptions2(data.supressShakingDialog, 
				data.isRemoteSystemIntegrationActive));
	}

}
