package jira.exception;

import com.dolby.jira.net.soap.jira.RemotePermissionException;

public class JiraPermissionException extends JiraException {

	private static final long serialVersionUID = 1L;
	
	public JiraPermissionException(RemotePermissionException e) {
		super(e);
	}
	
}
