package tasks.tasks.mock;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;

import jira.Jira;
import jira.JiraException;
import jira.JiraIssue;
import jira.JiraIssueData;

public class MockJira implements Jira {

	private Map<String, String> summaryByKey = new LinkedHashMap<String, String>();

	@Override
	public JiraIssue getIssueByKey(String key) throws JiraException {
		String summary = summaryByKey.get(key);
		
		if (summary == null){
			throw new JiraException();
		}
		
		JiraIssueData data = new JiraIssueData();
		data.setKey(key);
		data.setSummary(summary);
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

	public void addIssueKeyDescription(String key, String description) {
		summaryByKey.put(key, description);
		
	}

}
