package jira.events;

import java.io.Serializable;

public class SetJiraConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String url;
	private String username;
	private String password;
	
	public SetJiraConfig(String url, String username, String password)
	{
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}
	
	
	public String getPassword() {
		return password;
	}

}
