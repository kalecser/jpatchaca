package jira;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jira.exception.JiraException;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.dolby.jira.net.soap.jira.RemoteComment;
import com.dolby.jira.net.soap.jira.RemoteField;
import com.dolby.jira.net.soap.jira.RemoteFieldValue;
import com.dolby.jira.net.soap.jira.RemoteIssue;
import com.dolby.jira.net.soap.jira.RemoteNamedObject;
import com.dolby.jira.net.soap.jira.RemoteStatus;
import com.dolby.jira.net.soap.jira.RemoteWorklog;

public class JiraImpl implements Jira {

	private Map<String, String> statuses;
	private JiraServiceFacade jiraService;

	public JiraImpl(JiraServiceFacade jiraService) {
		this.jiraService = jiraService;
	}

	@Override
	public JiraIssue getIssueByKey(final String key) {
		Validate.notEmpty(key);		
		Logger.getLogger("org.apache.axis").setLevel(Level.OFF);
		return createIssue(jiraService.getIssueByKey(key));
	}

	@Override
	public void newWorklog(final String issueId, final Calendar startDate,
			final String timeSpent) {

		final RemoteWorklog workLog = new RemoteWorklog();
		workLog.setStartDate(startDate);
		workLog.setTimeSpent(timeSpent);

		jiraService.addWorklogAndAutoAdjustRemainingEstimate(issueId, workLog);
	}

	@Override
	public List<JiraAction> getAvaiableActions(JiraIssue issue) {
		Validate.notNull(issue);
		
		List<JiraAction> actions = new ArrayList<JiraAction>();
		RemoteNamedObject[] availableActions = jiraService
				.getAvailableActions(issue.getKey());

		if (availableActions == null)
			return actions;

		for (RemoteNamedObject namedObject : availableActions)
			actions.add(createAction(issue.getKey(), namedObject));

		return actions;
	}

	@Override
	public void progressWithAction(JiraIssue issue, JiraAction action,
			String comment) {

		if (comment == null || comment.isEmpty())
			throw new JiraException("Comment body can not be empty!");

		jiraService.progressWorkflowAction(issue.getKey(), action.getId(),
				new RemoteFieldValue[0]);

		jiraService.addComment(issue.getKey(), new RemoteComment(null, comment,
				null, null, null, null, null, null));
	}

	@Override
	public String getIssueAssignee(JiraIssue issue) {
		return getRemoteIssue(issue.getId()).getAssignee();
	}

	@Override
	public String getIssueStatus(JiraIssue issue) {
		String statusId = getRemoteIssue(issue.getId()).getStatus();
		return getStatuses().get(statusId);
	}
	
	@Override
	public void assignIssueToCurrentUser(JiraIssue issue) {
		String[] values = new String[] { jiraService.getJiraUsername() };
		RemoteFieldValue fieldValue = new RemoteFieldValue("assignee", values);
		RemoteFieldValue[] fieldValues = new RemoteFieldValue[] { fieldValue };
		jiraService.updateIssue(issue.getKey(), fieldValues);
	}

	@Override
	public boolean isAssignedToCurrentUser(JiraIssue issue) {
		String username = jiraService.getJiraUsername();
		return username.equals(getIssueAssignee(issue));
	}

	@Override
	public List<JiraIssue> getIssuesFromCurrentUserWithStatus(
			List<String> statusList) {

		Validate.notEmpty(statusList);
		
		List<JiraIssue> issues = new ArrayList<JiraIssue>();

		for (RemoteIssue remoteIssue : jiraService
				.getIssuesFromCurrentUserWithStatus(statusList))
			issues.add(createIssue(remoteIssue));

		return issues;
	}
	
	private RemoteIssue getRemoteIssue(final String id) {
		Validate.notEmpty(id);
		return jiraService.getIssueById(id);
	}

	private JiraIssue createIssue(final RemoteIssue issue) {
		final JiraIssueData data = new JiraIssueData();
		data.setId(issue.getId());
		data.setKey(issue.getKey());
		data.setSummary(issue.getSummary());
		return new JiraIssue(data);
	}	

	private JiraAction createAction(String key, RemoteNamedObject namedObject) {
		JiraAction action = new JiraAction(namedObject.getId(),
				namedObject.getName());

		RemoteField[] fields = jiraService.getFieldsForAction(key,
				namedObject.getId());

		for (RemoteField remoteField : fields)
			action.addField(createField(remoteField));
		return action;
	}

	private JiraField createField(RemoteField remoteField) {
		return new JiraField(remoteField.getId(), remoteField.getName());
	}

	private Map<String, String> getStatuses() {
		if (statuses != null)
			return statuses;

		RemoteStatus[] remoteStatuses = jiraService.getStatuses();
		statuses = new HashMap<String, String>();
		for (RemoteStatus remoteStatus : remoteStatuses)
			statuses.put(remoteStatus.getId(), remoteStatus.getName());

		return statuses;
	}
}