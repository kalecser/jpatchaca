package ui.swing.options;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import lang.Maybe;
import net.miginfocom.swing.MigLayout;
import ui.swing.options.OptionsScreenModel.Data;
import ui.swing.presenter.ActionPane;
import ui.swing.presenter.UIAction;

public class OptionsScreen implements ActionPane {

	private JCheckBox showLabels;

	private JTextField jiraUsername;
	private JTextField jiraPassword;
	private JTextField jiraUrl;
	private JCheckBox issueStatusManagementEnabled;
	private JCheckBox supressShakingDialog;
	private JCheckBox remoteSystemIntegration;
	private final OptionsScreenModel optionsScreenModel;


	public OptionsScreen(OptionsScreenModel optionsScreenModel) {
		this.optionsScreenModel = optionsScreenModel;
	}

	@Override
	public JPanel getPanel() {

		final JTabbedPane tab = new JTabbedPane();

		addMainPreferences(tab);
		addJiraPreferences(tab);
		addKeyboardRotationPreferences(tab);
		JPanel jPanel = new JPanel();
		jPanel.add(tab);

		populateControlsFromData();

		return jPanel;
	}

	private void populateControlsFromData() {
		Data data = optionsScreenModel.readDataFromSystem();

		supressShakingDialog.setSelected(data.supressShakingDialog);
		remoteSystemIntegration.setSelected(data.isRemoteSystemIntegrationActive);
		issueStatusManagementEnabled
				.setSelected(data.issueStatusManagementEnabled);
		if (data.jiraUrl != null) {
			jiraUrl.setText(data.jiraUrl.unbox());
		}
		if (data.jiraUserName != null) {
			jiraUsername.setText(data.jiraUserName.unbox());
		}
		if (data.jiraPassword != null) {
			jiraPassword.setText(data.jiraPassword.unbox());
		}
	}

	private void addKeyboardRotationPreferences(JTabbedPane tab) {
		final JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new MigLayout("wrap 1,fillx"));
		supressShakingDialog = new JCheckBox("Supress shaking dialog");
		optionsPanel.add(supressShakingDialog);
		optionsPanel.add(createRemoteIntegrationCheckbox());
		tab.add("Keyboard Rotation", optionsPanel);
	}

	private JCheckBox createRemoteIntegrationCheckbox() {
		remoteSystemIntegration = new JCheckBox("Sync with remote Jpatchaca when pair-programming");
		return remoteSystemIntegration;
	}

	private void addJiraPreferences(JTabbedPane tab) {
		final JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new MigLayout("wrap 4,fillx"));

		issueStatusManagementEnabled = new JCheckBox("Issue Status Management");
		optionsPanel.add(issueStatusManagementEnabled, "span 4");

		optionsPanel.add(new JLabel("Jira url"));
		jiraUrl = new JTextField();
		optionsPanel.add(jiraUrl, "growx,span 3");

		optionsPanel.add(new JLabel("Jira username"));
		jiraUsername = new JTextField(30);
		optionsPanel.add(jiraUsername, "growx,span 3");

		optionsPanel.add(new JLabel("Jira password"));
		jiraPassword = new JPasswordField(30);
		optionsPanel.add(jiraPassword, "growx,span 3");

		tab.add("Jira", optionsPanel);
	}

	private void addMainPreferences(JTabbedPane tab) {
		showLabels = new JCheckBox("Show labels");
		tab.add("Preferences", showLabels);
	}

	@Override
	public UIAction action() {
		return new UIAction() {
			@Override
			public void run() {
				doAction();
			}
		};
	}

	void doAction() {
		Data data = new Data();
		data.jiraUrl = Maybe.wrap(jiraUrl.getText());
		data.jiraUserName = Maybe.wrap(jiraUsername.getText());
		data.jiraPassword = Maybe.wrap(jiraPassword.getText());
		data.issueStatusManagementEnabled = issueStatusManagementEnabled
				.isSelected();
		data.supressShakingDialog = supressShakingDialog.isSelected();
		data.isRemoteSystemIntegrationActive = remoteSystemIntegration.isSelected(); 
		optionsScreenModel.writeDataIntoSystem(data);
	}

}