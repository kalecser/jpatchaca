package twitter;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

public class OptionsScreenOperator {

	private final JButtonOperator okButton;
	private final JDialogOperator dialog;

	private JCheckBoxOperator twitterEnabledBox;
	private JTextFieldOperator userNameField;
	private JTextFieldOperator passwordField;
	
	public OptionsScreenOperator() {
		dialog = new JDialogOperator("Options");
		okButton = new JButtonOperator(dialog, "ok");
		selectTwitterTab();
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


	private void selectTwitterTab(){
		JTabbedPaneOperator jTabbedPaneOperator = new JTabbedPaneOperator(dialog);
		jTabbedPaneOperator.selectPage("Twitter");
		userNameField = new JTextFieldOperator(dialog, 0);
		passwordField = new JTextFieldOperator(dialog, 1);
		twitterEnabledBox = new JCheckBoxOperator(dialog);
	}

}
