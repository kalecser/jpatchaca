package jira.exception;

public class JiraUserNotFound extends JiraException {

	private static final long serialVersionUID = 1L;
	
	public JiraUserNotFound(String username) {
		super("User not found: " + username);
	}

}
