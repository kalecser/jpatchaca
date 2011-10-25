package ui.swing.options;

import lang.Maybe;

public interface OptionsScreenModel {

	public abstract void setJiraConfig(String url, String username,
			String password, boolean issueStatusManagementEnabled);

	public abstract Maybe<String> jiraUrl();

	public abstract Maybe<String> jiraUserName();

	public abstract Maybe<String> jiraPassword();
	
	public abstract boolean isIssueStatusManagementEnabled();

	public abstract void setKeyboarRotationConfig(boolean supressShakingDialog);

	public abstract boolean supressShakingDialog();

	public abstract void isToUseOldIcons();

}