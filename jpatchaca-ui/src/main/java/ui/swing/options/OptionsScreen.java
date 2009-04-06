package ui.swing.options;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ui.swing.presenter.OkCancelPane;
import ui.swing.presenter.Presenter;
import wheel.swing.CheckboxSignalBinder;
import wheel.swing.TextFieldBinder;

public class OptionsScreen {

	public class OptionsScreenOkCancelPane implements OkCancelPane {

		private JCheckBox twitterEnabled;
		private JTextField username;
		private JTextField password;

		@Override
		public JPanel getPanel() {
			JPanel optionsPanel = new JPanel();
			optionsPanel.setLayout(new MigLayout("wrap 4,fillx"));

			twitterEnabled = new JCheckBox(
					"Twitter logging enabled");
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
			return optionsPanel;
		}

		@Override
		public Runnable okAction() {
			return new Runnable() {
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
			Presenter presenter) {
		this.optionsScreenModel = optionsScreenModel;
		this.presenter = presenter;
	}

	public synchronized void show() {

		if (optionsScreen != null) {
			optionsScreen.setVisible(true);
			optionsScreen.toFront();
			return;
		}

		optionsScreen = presenter.showOkCancelDialog(
				new OptionsScreenOkCancelPane(), "Options");

	}

	public static void main(String[] args) {
		new OptionsScreen(new OptionsScreenMock(), new Presenter()).show();
	}

}
