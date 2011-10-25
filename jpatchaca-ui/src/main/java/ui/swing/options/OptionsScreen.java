package ui.swing.options;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ui.swing.presenter.ActionPane;
import ui.swing.presenter.UIAction;

public class OptionsScreen implements ActionPane {

	private JCheckBox twitterEnabled;
	private JCheckBox showLabels;

	private JTextField jiraUsername;
	private JTextField jiraPassword;
	private JTextField jiraUrl;
	private JCheckBox issueStatusManagementEnabled;
	private JCheckBox supressShakingDialog;
	private final OptionsScreenModel optionsScreenModel;
	
	public OptionsScreen(OptionsScreenModel optionsScreenModel){
		this.optionsScreenModel = optionsScreenModel;
	}

	@Override
	public JPanel getPanel() {
		
		final JTabbedPane tab = new JTabbedPane();
		
		addMainPreferences(tab);
		addJiraPreferences(tab);
		addKeyboardRotationPreferences(tab);

		twitterEnabled.requestFocus();

		JPanel jPanel = new JPanel();
		jPanel.add(tab);
		return jPanel;
	}

	private void addKeyboardRotationPreferences(JTabbedPane tab) {
		final JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new MigLayout("wrap 1,fillx"));
		supressShakingDialog = new JCheckBox("Supress shaking dialog");
		supressShakingDialog.setSelected(optionsScreenModel.supressShakingDialog());
		optionsPanel.add(supressShakingDialog);
		tab.add("Keyboard Rotation",optionsPanel);
	}

	private void addJiraPreferences(JTabbedPane tab) {
		final JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new MigLayout("wrap 4,fillx"));

		issueStatusManagementEnabled = new JCheckBox("Issue Status Management");
		issueStatusManagementEnabled.setSelected(optionsScreenModel.isIssueStatusManagementEnabled());
		optionsPanel.add(issueStatusManagementEnabled, "span 4");
		
		optionsPanel.add(new JLabel("Jira url"));
		jiraUrl = new JTextField();
		if (optionsScreenModel.jiraUrl() != null) {
			jiraUrl.setText(optionsScreenModel.jiraUrl().unbox());
		}
		optionsPanel.add(jiraUrl, "growx,span 3");

		optionsPanel.add(new JLabel("Jira username"));
		jiraUsername = new JTextField(30);
		if (optionsScreenModel.jiraUserName() != null) {
			jiraUsername.setText(optionsScreenModel.jiraUserName().unbox());
		}
		optionsPanel.add(jiraUsername, "growx,span 3");

		optionsPanel.add(new JLabel("Jira password"));
		jiraPassword = new JPasswordField(30);
		if (optionsScreenModel.jiraPassword() != null) {
			jiraPassword.setText(optionsScreenModel.jiraPassword().unbox());
		}
		optionsPanel.add(jiraPassword, "growx,span 3");
		
		tab.add("Jira",optionsPanel);
	}
	
	private void addMainPreferences(JTabbedPane tab) {
		showLabels = new JCheckBox("Show labels");
		tab.add("Preferences",showLabels);
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
		optionsScreenModel.setJiraConfig(jiraUrl.getText(),
				jiraUsername.getText(), jiraPassword.getText(), issueStatusManagementEnabled.isSelected());
		
		optionsScreenModel.setKeyboarRotationConfig(supressShakingDialog.isSelected());
	}

}