package ui.swing.mainScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jira.JiraAction;
import jira.RemoteJiraIssue;

public class JiraActionFiredListener implements ActionListener {
	
	private final RemoteJiraIssue issue;
	private final JiraAction action;

	public JiraActionFiredListener(RemoteJiraIssue issue, JiraAction action)
	{
		this.issue = issue;
		this.action = action;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		issue.progressWithAction(action, "");
	}

}
