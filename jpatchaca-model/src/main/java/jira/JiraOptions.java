package jira;

import lang.Maybe;

public class JiraOptions {

	private Maybe<String> userName;
	private Maybe<String> password;
	private Maybe<String> url;

	public Maybe<String> getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = Maybe.wrap(userName);
	}

	public Maybe<String> getURL() {
		return url;
	}

	public void setURL(String address) {
		this.url = Maybe.wrap(address);
	}

	public Maybe<String> getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Maybe.wrap(password);
	}
}