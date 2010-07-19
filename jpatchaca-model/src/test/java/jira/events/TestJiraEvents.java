package jira.events;

import org.junit.Test;

public class TestJiraEvents {

	@Test
	public void ensureJiraEventsWillNotBeRenamedOrMoved() throws ClassNotFoundException{
		Class.forName("jira.events.SendWorklog");
		Class.forName("jira.events.SetJiraConfig");
		Class.forName("jira.events.SetJiraIssueToTask");
	}
}
