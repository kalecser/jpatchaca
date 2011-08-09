package jira.exception;

public class JiraOptionsNotSetException extends JiraException {

	private static final long serialVersionUID = 1L;
	
	public JiraOptionsNotSetException() {
		super("Jira Options not setted.");
	}

}
