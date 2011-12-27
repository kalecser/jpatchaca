package jira.issue;

import java.util.List;

import jira.service.Jira;

public class RemoteJiraIssue {

	private final JiraIssue issue;
	private final Jira jira;

	public RemoteJiraIssue(Jira jira, JiraIssue issue) {
		this.jira = jira;
		this.issue = issue;
	}

	public String getKey() {
		return issue.getKey();
	}

	public String getSummary() {
		return issue.getSummary();
	}

	public String getStatus() {
		return jira.getIssueStatus(issue);
	}

	public String getAssignee() {
		return jira.getIssueAssignee(issue);
	}

	public void progressWithAction(JiraAction action, String comment) {
		jira.progressWithAction(issue, action, comment);
	}

	public List<JiraAction> getAvaiableActions() {
		return jira.getAvaiableActions(issue);
	}

	public boolean isWorkable(){
		
		return jira.isWorkable(issue);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RemoteJiraIssue))
			return false;

		return issue.equals(((RemoteJiraIssue) obj).issue);
	}

	public void assignToCurrentUser() {
		jira.assignIssueToCurrentUser(issue);
	}

	public boolean isAssignedToCurrentUser() {
		return jira.isAssignedToCurrentUser(issue);
	}
}
