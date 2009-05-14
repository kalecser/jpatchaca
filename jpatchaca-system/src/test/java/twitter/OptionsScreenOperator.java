package twitter;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

public class OptionsScreenOperator {

	private final JCheckBoxOperator twitterEnabledBox;
	private final JTextFieldOperator userNameField;
	private final JTextFieldOperator passwordField;
	private final JButtonOperator okButton;
	private final JDialogOperator dialog;

	public OptionsScreenOperator() {
		dialog = new JDialogOperator("Options");
		twitterEnabledBox = new JCheckBoxOperator(dialog);
		userNameField = new JTextFieldOperator(dialog, 0);
		passwordField = new JTextFieldOperator(dialog, 1);
		okButton = new JButtonOperator(dialog, "ok");
	}

	public void fillAndPressOk(final boolean twitterEnabled,
			final String usern, final String pass) {

		twitterEnabledBox.setSelected(true);
		userNameField.setText(usern);
		passwordField.setText(pass);
		okButton.push();

	}

	public Boolean twitterEnabled() {
		return twitterEnabledBox.isSelected();
	}

	public String twitterPassword() {
		return passwordField.getText();
	}

	public String twitterUserName() {
		return userNameField.getText();
	}

	public void release() {
		dialog.setVisible(false);
		dialog.dispose();
	}

}
