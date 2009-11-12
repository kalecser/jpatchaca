package jira;

import org.reactive.Source;

public class JiraConfig {

	private final Source<String> _userName = new Source<String>("");
	private final Source<String> _password = new Source<String>("");
	private final Source<String> _address = new Source<String>("");
	
	public void supplyUserName(String userName) {
		_userName.supply(userName);
	}

	public void supplyPassword(String password) {
		_password.supply(password);
	}

	public void supplyJiraAddress(String address) {
		_address.supply(address);
	}

	public final Source<String> userName() {
		return _userName;
	}

	public final Source<String> password() {
		return _password;
	}

	public final Source<String> address() {
		return _address;
	}

}
