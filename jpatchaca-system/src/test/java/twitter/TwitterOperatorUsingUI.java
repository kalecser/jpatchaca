package twitter;

import tasks.adapters.ui.MainScreenOperator;

public class TwitterOperatorUsingUI implements TwitterOperator {

	private MainScreenOperator mainScreen;
	
	public TwitterOperatorUsingUI(){
		mainScreen = new MainScreenOperator();	
	}
	
	@Override
	public void enableTwitterLogging(String usern, String pass) {
		mainScreen.pushOptionsMenu();
		OptionsScreenOperator optionsScreen = new OptionsScreenOperator();
		optionsScreen.fillAndPressOk(true, usern, pass);
		
	}

	@Override
	public Boolean twitterEnabled() {
		mainScreen.pushOptionsMenu();
		OptionsScreenOperator optionsScreen = new OptionsScreenOperator();
		return optionsScreen.twitterEnabled();
	}

	@Override
	public String twitterPassword() {
		mainScreen.pushOptionsMenu();
		OptionsScreenOperator optionsScreen = new OptionsScreenOperator();
		return optionsScreen.twitterPassword();
	}

	@Override
	public String twitterUserName() {
		mainScreen.pushOptionsMenu();
		OptionsScreenOperator optionsScreen = new OptionsScreenOperator();
		return optionsScreen.twitterUserName();
	}

}
