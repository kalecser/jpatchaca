package twitter;

import tasks.adapters.ui.MainScreenOperator;

public class TwitterOperatorUsingUI implements TwitterOperator {

	private final MainScreenOperator mainScreen;

	public TwitterOperatorUsingUI() {
		mainScreen = new MainScreenOperator();
	}

	@Override
	public void enableTwitterLogging(final String usern, final String pass) {
		mainScreen.pushOptionsMenu();
		final OptionsScreenOperator optionsScreen = new OptionsScreenOperator();
		optionsScreen.fillAndPressOk(true, usern, pass);
		optionsScreen.release();

	}

	@Override
	public Boolean twitterEnabled() {
		mainScreen.pushOptionsMenu();
		final OptionsScreenOperator optionsScreen = new OptionsScreenOperator();
		final Boolean twitterEnabled = optionsScreen.twitterEnabled();
		optionsScreen.release();
		return twitterEnabled;
	}

	@Override
	public String twitterPassword() {
		mainScreen.pushOptionsMenu();
		final OptionsScreenOperator optionsScreen = new OptionsScreenOperator();
		final String twitterPassword = optionsScreen.twitterPassword();
		optionsScreen.release();
		return twitterPassword;
	}

	@Override
	public String twitterUserName() {
		mainScreen.pushOptionsMenu();
		final OptionsScreenOperator optionsScreen = new OptionsScreenOperator();
		final String twitterUserName = optionsScreen.twitterUserName();
		optionsScreen.release();
		return twitterUserName;
	}

}
