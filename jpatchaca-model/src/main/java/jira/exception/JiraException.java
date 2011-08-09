package jira.exception;

public class JiraException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JiraException() {
		super();
	}

	public JiraException(String message, Throwable cause) {
		super(message, cause);
	}

	public JiraException(String message) {
		super(message);
	}

	public JiraException(Throwable cause) {
		super(cause);
	}
	
}
