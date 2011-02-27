package ui.swing.mainScreen.tasks.mock;

import java.util.Calendar;

import org.apache.commons.lang.NotImplementedException;

import jira.Jira;
import jira.JiraException;
import jira.JiraIssue;
import jira.JiraIssueData;

public class MockJira implements Jira {

	@Override
	public JiraIssue getIssueByKey(String key) throws JiraException {
		JiraIssueData data = new JiraIssueData();
		data.setKey(key);
		data.setSummary("jira-issue-summary");
		return new JiraIssue(data);
	}

	@Override
	public JiraIssue getIssueById(String id) throws JiraException {
		throw new NotImplementedException();
	}

	@Override
	public void newWorklog(String issueId, Calendar startDate, String timeSpent) {
		throw new NotImplementedException();
	}

}
