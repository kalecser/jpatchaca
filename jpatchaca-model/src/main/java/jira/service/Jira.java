package jira.service;

import java.util.Calendar;
import java.util.List;

import jira.exception.JiraException;
import jira.issue.JiraAction;
import jira.issue.JiraIssue;

public interface Jira {

	public final String WORKABLE_META_ATTRIBUTE = "minyaa.jira.issue.workable";
	
	JiraIssue getIssueByKey(String key) throws JiraException;

	void newWorklog(String issueId, Calendar startDate, String timeSpent);
	
	List<JiraAction> getAvaiableActions(JiraIssue issue);

	void progressWithAction(JiraIssue issue, JiraAction action, String comment);
	
	String getIssueStatus(JiraIssue issue);

	String getIssueAssignee(JiraIssue issue);

	void assignIssueToCurrentUser(JiraIssue issue);

	boolean isAssignedToCurrentUser(JiraIssue issue);

	List<JiraIssue> getIssuesFromCurrentUserWithStatus(List<String> statusList);

	boolean isWorkable(JiraIssue issue);	
}
