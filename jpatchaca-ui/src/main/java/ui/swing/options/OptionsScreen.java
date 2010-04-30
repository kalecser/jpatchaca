package ui.swing.options;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		private JTextField username;
		private JTextField password;

		@Override
		public JPanel getPanel() {
			final JPanel optionsPanel = new JPanel();
			optionsPanel.setLayout(new MigLayout("wrap 4,fillx"));

			twitterEnabled = new JCheckBox("Twitter logging enabled");
			CheckboxSignalBinder.bind(twitterEnabled, optionsScreenModel
					.twitterEnabled());
			optionsPanel.add(twitterEnabled, "span 4");

			optionsPanel.add(new JLabel("Twitter username"));
			username = new JTextField(30);
			TextFieldBinder.bind(username, optionsScreenModel.userName());
			optionsPanel.add(username, "growx,span 3");

			optionsPanel.add(new JLabel("Twitter Password"));
			password = new JTextField(30);
			TextFieldBinder.bind(password, optionsScreenModel.password());
			optionsPanel.add(password, "growx,span 3");

			showLabels = new JCheckBox("Show labels");
			CheckboxSignalBinder.bind(twitterEnabled, optionsScreenModel
					.twitterEnabled());
			optionsPanel.add(showLabels, "span 4");

			twitterEnabled.requestFocus();

			return optionsPanel;
		}

		@Override
		public UIAction action() {
			return new UIAction() {
				@Override
				public void run() {
					optionsScreenModel.setConfig(twitterEnabled.isSelected(),
							username.getText(), password.getText());
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
