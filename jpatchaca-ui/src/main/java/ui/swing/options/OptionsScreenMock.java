package ui.swing.options;

import lang.Maybe;

import org.reactive.Signal;
import org.reactive.Source;

public class OptionsScreenMock implements OptionsScreenModel {

	Source<String> password = new Source<String>("bar");

	@Override
	public Signal<String> twitterPassword() {
		return password;
	}

	@Override
	public void setTwitterConfig(final boolean selected, final String username,
			final String password) {

	}

	@Override
	public void setJiraConfig(final String url, final String username,
			final String password, final boolean issueStatusManagementEnabled) {

	}

	@Override
	public Signal<Boolean> twitterEnabled() {
		return new Source<Boolean>(true);
	}

	@Override
	public Signal<String> twitterUserName() {
		return new Source<String>("bar");
	}

	@Override
	public Maybe<String> jiraPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Maybe<String> jiraUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Maybe<String> jiraUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isIssueStatusManagementEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
