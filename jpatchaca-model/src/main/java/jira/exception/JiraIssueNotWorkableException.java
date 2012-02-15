package jira.exception;

import jira.issue.JiraIssue;

public class JiraIssueNotWorkableException extends JiraException {

	private static final long serialVersionUID = 1L;
	
	public JiraIssueNotWorkableException(JiraIssue issue) {
		super("Issue " + issue.getKey() + " is not workable.");
	}

}
