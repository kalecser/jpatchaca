package ui.swing.options;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ui.swing.presenter.ActionPane;
import ui.swing.presenter.Presenter;
import ui.swing.presenter.UIAction;
import wheel.swing.CheckboxSignalBinder;
import wheel.swing.TextFieldBinder;

public class OptionsScreen {

	public class OptionsScreenOkCancelPane implements ActionPane {

		private JCheckBox twitterEnabled;
		private JCheckBox showLabels;
		private JTextField twitterUsername;
		private JTextField twitterPassword;

		private JTextField jiraUsername;
		private JTextField jiraPassword;
		private JTextField jiraUrl;

		@Override
		public JPanel getPanel() {
			final JPanel optionsPanel = new JPanel();
			optionsPanel.setLayout(new MigLayout("wrap 4,fillx"));

			twitterEnabled = new JCheckBox("Twitter logging enabled");
			CheckboxSignalBinder.bind(twitterEnabled, optionsScreenModel
					.twitterEnabled());
			optionsPanel.add(twitterEnabled, "span 4");

			optionsPanel.add(new JLabel("Twitter username"));
			twitterUsername = new JTextField(30);
			TextFieldBinder.bind(twitterUsername, optionsScreenModel
					.twitterUserName());
			optionsPanel.add(twitterUsername, "growx,span 3");

			optionsPanel.add(new JLabel("Twitter Password"));
			twitterPassword = new JPasswordField(30);
			
			TextFieldBinder.bind(twitterPassword, optionsScreenModel
					.twitterPassword());
			optionsPanel.add(twitterPassword, "growx,span 3");

			showLabels = new JCheckBox("Show labels");
			CheckboxSignalBinder.bind(twitterEnabled, optionsScreenModel
					.twitterEnabled());
			optionsPanel.add(showLabels, "span 4");

			optionsPanel.add(new JSeparator(), "growx,span 4");

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

			twitterEnabled.requestFocus();

			return optionsPanel;
		}

		@Override
		public UIAction action() {
			return new UIAction() {
				@Override
				public void run() {
					optionsScreenModel.setTwitterConfig(twitterEnabled
							.isSelected(), twitterUsername.getText(),
							twitterPassword.getText());
					optionsScreenModel.setJiraConfig(jiraUrl.getText(),
							jiraUsername.getText(), jiraPassword.getText());
				}
			};
		}

	}

	private JDialog optionsScreen;
	private final OptionsScreenModel optionsScreenModel;
	private final Presenter presenter;

	public OptionsScreen(final OptionsScreenModel optionsScreenModel,
			final Presenter presenter) {
		this.optionsScreenModel = optionsScreenModel;
		this.presenter = presenter;
	}

	public synchronized void show() {

		if (optionsScreen != null) {
			optionsScreen.setVisible(false);
			optionsScreen.dispose();
		}

		optionsScreen = presenter.showOkCancelDialog(
				new OptionsScreenOkCancelPane(), "Options");

	}

	public static void main(final String[] args) {
		new OptionsScreen(new OptionsScreenMock(), new Presenter(null)).show();
	}

}
