package tasks.tasks.mock;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jira.JiraOptions;
import jira.exception.JiraException;
import jira.issue.JiraAction;
import jira.issue.JiraIssue;
import jira.issue.JiraIssueData;
import jira.service.Jira;

import org.apache.commons.lang.NotImplementedException;

public class MockJira implements Jira {

	private Map<String, String> summaryByKey = new LinkedHashMap<String, String>();
	private Map<String, String> statusByKey = new LinkedHashMap<String, String>();
	private Map<String, String> assigneeByKey = new LinkedHashMap<String, String>();
	private Map<String, List<String>> actionsByKey = new LinkedHashMap<String, List<String>>();	
	private final JiraOptions options;

	public MockJira(JiraOptions options)
	{
		this.options = options;		
	}
	
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
	public void newWorklog(String issueId, Calendar startDate, String timeSpent) {
		throw new NotImplementedException();

	}

	public void addIssueKeyDescription(String key, String description) {
		summaryByKey.put(key, description);
	}
	
	public void addIssueKeyData(String key, String summary, String status, String assignee) {
		summaryByKey.put(key, summary);
		statusByKey.put(key, status);
		assigneeByKey.put(key, assignee);
	}
	
	public void addAvaiableAction(String key, String action)
	{
		List<String> actions = actionsByKey.get(key);
		if(actions == null)
		{
			actions = new LinkedList<String>();
			actionsByKey.put(key, actions);
		}
		
		actions.add(action);
	}

	@Override
	public List<JiraAction> getAvaiableActions(JiraIssue issue) {
		List<String> actions = actionsByKey.get(issue.getKey());
		LinkedList<JiraAction> jiraActions = new LinkedList<JiraAction>();	
		if(actions != null)
			for(String action: actions)
				jiraActions.add(new JiraAction("0", action));
			
		return jiraActions;
	}

	@Override
	public void progressWithAction(JiraIssue issue, JiraAction action, String comment) {
		throw new NotImplementedException();
	}

	@Override
	public List<JiraIssue> getIssuesFromCurrentUserWithStatus(List<String> statusList) {
		List<JiraIssue> result = new LinkedList<JiraIssue>();
		for(Entry<String, String> entry: assigneeByKey.entrySet()){			
			String issueKey = entry.getKey();
			String issueAssignee = entry.getValue(); 
			if(!isAssignedToUser(issueAssignee))
				continue;
			
			if(statusList.contains(statusByKey.get(issueKey)))
				result.add(getIssueByKey(issueKey));
		}
		return result;			
	}

	private boolean isAssignedToUser(String issueAssignee) {
		return options.getUserName() != null && options.getUserName().unbox().equals(issueAssignee);
	}

	@Override
	public String getIssueStatus(JiraIssue issue) {
		String status = statusByKey.get(issue.getKey());		
		if (status == null)
			throw new JiraException();
		return status;
	}

	@Override
	public String getIssueAssignee(JiraIssue issue) {
		String assignee = assigneeByKey.get(issue.getKey());		
		if (assignee == null)
			throw new JiraException();
		return assignee;
	}

	@Override
	public void assignIssueToCurrentUser(JiraIssue issue) {
		throw new NotImplementedException();
	}
	
	@Override
	public boolean isAssignedToCurrentUser(JiraIssue issue) {
		String assignee = assigneeByKey.get(issue.getKey());		
		return isAssignedToUser(assignee);
	}

	@Override
	public boolean isWorkable(JiraIssue issue) {
		return true;
	}
}
