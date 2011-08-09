package jira;

import java.util.Calendar;
import java.util.List;

import jira.exception.JiraException;

public interface Jira {

	JiraIssue getIssueByKey(String key) throws JiraException;

	JiraIssue getIssueById(String id) throws JiraException;

	void newWorklog(String issueId, Calendar startDate, String timeSpent);
	
	List<JiraAction> getAvaiableActions(JiraIssue issue);

	void progressWithAction(JiraIssue issue, JiraAction action, String comment);
	
	String getIssueStatus(JiraIssue issue);

	String getIssueAssignee(JiraIssue issue);

	void assignIssueToCurrentUser(JiraIssue issue);

	boolean isAssignedToCurrentUser(JiraIssue issue);

	List<JiraIssue> getIssuesFromCurrentUserWithStatus(List<String> statusList);
	
}
