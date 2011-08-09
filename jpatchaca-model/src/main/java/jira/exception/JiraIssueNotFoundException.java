package jira.exception;

public class JiraIssueNotFoundException extends JiraException {

	private static final long serialVersionUID = 1L;
	
	public JiraIssueNotFoundException(String key)
	{
		super(key);
	}

}
