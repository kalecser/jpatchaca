package jira;

import java.util.ArrayList;
import java.util.List;

import jira.issue.JiraAction;
import jira.issue.JiraIssue;
import jira.issue.RemoteJiraIssue;
import jira.service.Jira;

public class JiraIssueStatusManagement {

	// #TODO a relacao entre as classes JiraIssueStatusManagement e JiraIssueStatusActionPane estranha
	// #TODO remover status "Chumbados"
	
	public static List<String> developmentStatusList;
	public static List<String> impedimentStatusList;
	private final Jira jira;

	static {
		developmentStatusList = new ArrayList<String>();
		developmentStatusList.add("Desenvolvimento");
		developmentStatusList.add("Tratamento");

		impedimentStatusList = new ArrayList<String>();
		impedimentStatusList.add("Impedido");
	}

	public JiraIssueStatusManagement(final Jira jira) {
		this.jira = jira;
	}

	public List<RemoteJiraIssue> issuesForUpdate(JiraIssue startingIssue) {
		List<RemoteJiraIssue> issuesInDevelopment = issuesInDevelopment();

		RemoteJiraIssue remoteIssue = new RemoteJiraIssue(jira, startingIssue);
		if (remoteIssue.isAssignedToCurrentUser() && issuesInDevelopment.contains(remoteIssue))
			issuesInDevelopment.remove(remoteIssue);
		else
			issuesInDevelopment.add(0, remoteIssue);

		return issuesInDevelopment;
	}

	private List<RemoteJiraIssue> issuesInDevelopment() {
		ArrayList<RemoteJiraIssue> issues = new ArrayList<RemoteJiraIssue>();

		List<JiraIssue> issuesInDevelopment = jira
				.getIssuesFromCurrentUserWithStatus(JiraIssueStatusManagement.developmentStatusList);
		
		for (JiraIssue issue : issuesInDevelopment) {
			RemoteJiraIssue remoteIssue = new RemoteJiraIssue(jira, issue);
			if(!remoteIssue.getAvaiableActions().isEmpty())
				issues.add(remoteIssue);
		}

		return issues;
	}

	public void progressIssue(RemoteJiraIssue issue, JiraAction action, String comment) {
		issue.progressWithAction(action, comment);
	}

	public void assignToCurrentUser(RemoteJiraIssue issue) {
		issue.assignToCurrentUser();
	}

}
