package jira;

import java.util.Calendar;

import org.apache.commons.lang.NotImplementedException;

public class JiraMock implements Jira {

	@Override
	public JiraIssue getIssueByKey(String key) throws JiraException {
		JiraIssueData data = new JiraIssueData();
		data.setKey(key);
		data.setSummary("test");
		return new JiraIssue(data);
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
