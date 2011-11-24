package jira;

import jira.exception.JiraOptionsNotSetException;
import lang.Maybe;

public class JiraOptions {

	private Maybe<String> userName;
	private Maybe<String> password;
	private Maybe<String> url;
	private boolean issueStatusManagementEnabled;

	public Maybe<String> getUserName() {
		return userName;
	}

	public Maybe<String> getURL() {
		return url;
	}

	public Maybe<String> getPassword() {
		return password;
	}

	public void setUserName(String userName) {
		if (userName.trim().equals(""))
			userName = null;
		this.userName = Maybe.wrap(userName);
	}

	public void setPassword(String password) {
		if (password.trim().equals(""))
			password = null;
		this.password = Maybe.wrap(password);
	}

	public void setURL(String address) {
		if (address.trim().equals(""))
			address= null;
		this.url = Maybe.wrap(address);
	}

	public boolean isJiraEnabled() {
		return this.password != null;
	}

	public boolean isIssueStatusManagementEnabled() {
		return issueStatusManagementEnabled;
	}

	public void setIssueStatusManagementEnabled(
			boolean issueStatusManagementEnabled) {
		this.issueStatusManagementEnabled = issueStatusManagementEnabled;
	}

	protected String getServiceURL() {
		if (url == null)
			throw new JiraOptionsNotSetException();

		return String.format("%s/rpc/soap/jirasoapservice-v2", url);
	}
	
	public void validate(){
		if(userName == null || password == null || url == null)
			throw new JiraOptionsNotSetException();
	}
}