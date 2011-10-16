package ui.swing.jira;

import java.util.List;

import jira.JiraIssue;
import jira.JiraOptions;
import jira.RemoteJiraIssue;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import tasks.tasks.mock.MockJira;
import ui.swing.presenter.PatchacaPrintToConsoleExceptionHandler;
import ui.swing.presenter.Presenter;
import ui.swing.presenter.PresenterImpl;
import ui.swing.utils.UIEventsExecutorImpl;

public class JiraIssueStatusManagementTest {

	JiraOptions jiraOptions = new JiraOptions();
	MockJira jira = new MockJira(jiraOptions);
	JiraIssueStatusManagement statusManagement = new JiraIssueStatusManagement(jira);

	@Before
	public void setUp() {
		jiraOptions.setUserName("teste");
		jiraOptions.setPassword("teste");
	}

	@Test
	public void testOneIssueInDevelopment() throws InterruptedException {
		addIssueInDevelopment("ISSUE-1");
		
		JiraIssue startingIssue = jira.getIssueByKey("ISSUE-1");		
		List<RemoteJiraIssue> issuesForUpdate = statusManagement.issuesForUpdate(startingIssue);		
		assertIssues(issuesForUpdate);
	}
	
	@Test
	public void testOneIssueNotInDevelopment() throws InterruptedException {
		addIssueInInpediment("ISSUE-1");		
		JiraIssue startingIssue = jira.getIssueByKey("ISSUE-1");
		List<RemoteJiraIssue> issuesForUpdate = statusManagement.issuesForUpdate(startingIssue);		
		assertIssues(issuesForUpdate, "ISSUE-1");
	}
	
	@Test
	public void testOneIssueInDevelopmentOtherAssignee() throws InterruptedException {		
		jira.addIssueKeyData("ISSUE-1", "Jira Issue 1", "Desenvolvimento", "outro teste");
		JiraIssue startingIssue = jira.getIssueByKey("ISSUE-1");
		List<RemoteJiraIssue> issuesForUpdate = statusManagement.issuesForUpdate(startingIssue);
		
		assertIssues(issuesForUpdate, "ISSUE-1");
	}
	
	@Test
	public void testTwoIssuesInDevelopment() throws InterruptedException {
		addIssueInDevelopment("ISSUE-1");
		addIssueInDevelopment("ISSUE-2");
		
		JiraIssue startingIssue = jira.getIssueByKey("ISSUE-1");
		List<RemoteJiraIssue> issuesForUpdate = statusManagement.issuesForUpdate(startingIssue);
		
		assertIssues(issuesForUpdate, "ISSUE-2");
	}
	
	@Test
	public void testTwoIssuesDevelopmentAndImpediment() throws InterruptedException {
		addIssueInDevelopment("ISSUE-1");
		addIssueInInpediment("ISSUE-2");
		
		JiraIssue startingIssue = jira.getIssueByKey("ISSUE-1");		
		List<RemoteJiraIssue> issuesForUpdate = statusManagement.issuesForUpdate(startingIssue);		
		assertIssues(issuesForUpdate);
	}
	
	@Test
	public void testTwoIssuesInImpediment() throws InterruptedException {
		addIssueInInpediment("ISSUE-1");
		addIssueInInpediment("ISSUE-2");
		
		JiraIssue startingIssue = jira.getIssueByKey("ISSUE-1");		
		List<RemoteJiraIssue> issuesForUpdate = statusManagement.issuesForUpdate(startingIssue);		
		assertIssues(issuesForUpdate, "ISSUE-1");
	}
	
	@Test
	public void testTwoIssuesImpedimentAndDevelopment() throws InterruptedException {
		addIssueInInpediment("ISSUE-1");
		addIssueInDevelopment("ISSUE-2");
		
		JiraIssue startingIssue = jira.getIssueByKey("ISSUE-1");		
		List<RemoteJiraIssue> issuesForUpdate = statusManagement.issuesForUpdate(startingIssue);		
		assertIssues(issuesForUpdate, "ISSUE-1", "ISSUE-2");
	}
	
	private void addIssueInDevelopment(String key)
	{
		addMockIssue(key, "Desenvolvimento", "Impedir");
	}
	
	private void addIssueInInpediment(String key)
	{
		addMockIssue(key, "Impedido", "Desimpedir (Desenvolvimento)");
	}

	private void addMockIssue(String key, String status, String avaiableAction) {
		jira.addIssueKeyData(key, key, status, "teste");
		jira.addAvaiableAction(key, avaiableAction);
	}
	
	private void assertIssues(List<RemoteJiraIssue> issuesForUpdate, String... issueKeys) {
		Assert.assertEquals(issueKeys.length, issuesForUpdate.size());
		for(int i = 0; i < issueKeys.length; i++)
			Assert.assertEquals("Issue at " + i, issueKeys[i], issuesForUpdate.get(i).getKey());
	}
}
