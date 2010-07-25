package tasks;

import org.junit.Test;

public abstract class JiraIntegrationTest {

	
	public abstract PatchacaTasksOperator operator();
	PatchacaTasksOperator operator = operator();
	
	@Test
	public void testCreateTaskWithJiraIssue(){
		operator.createTaskWithJiraIntegration("task-name", "jira-key");
		operator.assertJiraKeyForTask("jira-key", "task-name");
	}
	
	@Test
	public void testEditJiraIssue(){
		operator.createTaskWithJiraIntegration("test", "jira-key");
		operator.ediTaskJiraKey("test", "jira-new-key");
		
		operator.assertJiraKeyForTask("jira-new-key", "test");
	}
	
}
