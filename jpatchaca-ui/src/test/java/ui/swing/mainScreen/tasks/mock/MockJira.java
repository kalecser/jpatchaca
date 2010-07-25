package ui.swing.mainScreen.tasks.mock;

import java.util.Calendar;

import org.apache.commons.lang.NotImplementedException;

import jira.Jira;
import jira.JiraException;
import jira.JiraIssue;

public class MockJira implements Jira {

	@Override
	public JiraIssue getIssueByKey(String key) throws JiraException {
		throw new NotImplementedException();
	}

	@Override
	public JiraIssue getIssueById(String id) throws JiraException {
		throw new NotImplementedException();
	}

	@Override
	public void newWorklog(String issueId, Calendar startDate, String timeSpent)
			throws JiraException {
		throw new NotImplementedException();
	}

}
