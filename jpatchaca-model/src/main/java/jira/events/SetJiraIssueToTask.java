package jira.events;

import java.io.Serializable;

import jira.JiraIssue;
import core.ObjectIdentity;

public class SetJiraIssueToTask implements Serializable {

	private static final long serialVersionUID = 1L;

	private final ObjectIdentity taskId;
	private final JiraIssue jiraIssue;

	public SetJiraIssueToTask(final ObjectIdentity taskId,
			final JiraIssue jiraIssue) {
		this.taskId = taskId;
		this.jiraIssue = jiraIssue;
	}

	public ObjectIdentity getTaskId() {
		return taskId;
	}

	public JiraIssue getJiraIssueId() {
		return jiraIssue;
	}
}
