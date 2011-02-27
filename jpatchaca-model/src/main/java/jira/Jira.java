package jira;

import java.util.Calendar;

public interface Jira {

	JiraIssue getIssueByKey(String key) throws JiraException;

	JiraIssue getIssueById(String id) throws JiraException;

	void newWorklog(String issueId, Calendar startDate, String timeSpent);

}
