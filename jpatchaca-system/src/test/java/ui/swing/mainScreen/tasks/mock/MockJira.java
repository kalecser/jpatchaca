package ui.swing.mainScreen.tasks.mock;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import jira.Jira;
import jira.JiraAction;
import jira.JiraIssue;
import jira.JiraIssueData;
import jira.exception.JiraException;

public class MockJira implements Jira {

	@Override
	public JiraIssue getIssueByKey(String key) throws JiraException {
		JiraIssueData data = new JiraIssueData();
		data.setKey(key);
		data.setSummary("jira-issue-summary");
		return new JiraIssue(data);
	}

	@Override
	public void newWorklog(String issueId, Calendar startDate, String timeSpent) {
		throw new NotImplementedException();
	}

	@Override
	public List<JiraAction> getAvaiableActions(JiraIssue issue) {
		throw new NotImplementedException();
	}

	@Override
	public void progressWithAction(JiraIssue issue, JiraAction action, String comment) {
		throw new NotImplementedException();
	}

	@Override
	public List<JiraIssue> getIssuesFromCurrentUserWithStatus(List<String> statusList) {
		throw new NotImplementedException();
	}

	@Override
	public String getIssueStatus(JiraIssue issue) {
		throw new NotImplementedException();
	}

	@Override
	public String getIssueAssignee(JiraIssue issue) {
		throw new NotImplementedException();
	}

	@Override
	public void assignIssueToCurrentUser(JiraIssue issue) {
		throw new NotImplementedException();
	}

	@Override
	public boolean isAssignedToCurrentUser(JiraIssue issue) {
		throw new NotImplementedException();
	}
}
