package jira.events;

import jira.exception.JiraIssueNotWorkableException;
import jira.issue.JiraIssue;
import jira.issue.RemoteJiraIssue;
import jira.service.Jira;
import core.ObjectIdentity;

public class JiraEventFactory {

	private final Jira jira;

	public JiraEventFactory(Jira jira){
		this.jira = jira;		
	}
	
	public SetJiraIssueToTask createSetIssueToTaskEvent(ObjectIdentity taskId, JiraIssue issue){
		
		RemoteJiraIssue remoteIssue = new RemoteJiraIssue(jira, issue);
		if(!remoteIssue.isWorkable())
			throw new JiraIssueNotWorkableException(issue);
		
		return new SetJiraIssueToTask(taskId, issue);
	}	
}
