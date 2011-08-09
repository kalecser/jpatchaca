package ui.swing.options;

import lang.Maybe;

import org.reactive.Signal;

public interface OptionsScreenModel {

	public abstract void setTwitterConfig(boolean selected, String username,
			String password);

	public abstract void setJiraConfig(String url, String username,
			String password, boolean issueStatusManagementEnabled);

	public abstract Signal<Boolean> twitterEnabled();

	public abstract Signal<String> twitterUserName();

	public abstract Signal<String> twitterPassword();

	public abstract Maybe<String> jiraUrl();

	public abstract Maybe<String> jiraUserName();

	public abstract Maybe<String> jiraPassword();
	
	public abstract boolean isIssueStatusManagementEnabled();

}