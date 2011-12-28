package ui.swing.mainScreen;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import tasks.TaskView;

import jira.JiraOptions;
import jira.issue.JiraIssue;
import lang.Maybe;

public class JiraBrowserIntegrationImpl implements IssueTrackerBrowserIntegration {
	
	private final JiraOptions jiraOptions;


	public JiraBrowserIntegrationImpl(JiraOptions jiraOptions){
		this.jiraOptions = jiraOptions;
	}
	
	
	@Override
	public void openJiraIssueOnBrowser(TaskView task) {
		if (task.getJiraIssue() == null)
			return;
		JiraIssue jiraIssue = task.getJiraIssue().unbox();
		openInBrowser(jiraIssue);
	}


	private void openInBrowser(JiraIssue jiraIssue) {
		final Maybe<String> jiraUrl = jiraOptions.getURL();

		if (jiraUrl == null) {
			return;
		}
		
		final String url = "/browse/"
				+ jiraIssue.getKey();
		try {
			final URI uri = new URI(jiraUrl.unbox() + url);
			Desktop.getDesktop().browse(uri);
		} catch (final IOException e1) {
			throw new RuntimeException(e1);
		} catch (final URISyntaxException e1) {
			throw new RuntimeException(e1);
		}
	}

	
}
