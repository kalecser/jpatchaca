package jira;

import java.util.Calendar;

import jira.exception.JiraIssueNotFoundException;
import jira.issue.JiraIssue;
import jira.issue.JiraIssueData;
import jira.mock.JiraMockInvocationHandler;
import jira.mock.JiraServiceMockFactory;
import jira.mock.MethodCall;
import jira.service.TokenManager;
import jira.service.Jira;
import jira.service.JiraImpl;
import jira.service.JiraServiceFacade;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.picocontainer.DefaultPicoContainer;

import basic.mock.MockHardwareClock;

import com.dolby.jira.net.soap.jira.RemoteIssue;
import com.dolby.jira.net.soap.jira.RemoteStatus;
import com.dolby.jira.net.soap.jira.RemoteValidationException;
import com.dolby.jira.net.soap.jira.RemoteWorklog;

public class JiraTest {

	private Jira jira;
	private JiraMockInvocationHandler jiraMockHandler;
	private MockHardwareClock clock;

	@Before
	public void setUp() {

		DefaultPicoContainer picoContainer = new DefaultPicoContainer();

		picoContainer.addComponent(_jiraOptions());
		picoContainer.addComponent(_serviceFactory());
		picoContainer.addComponent(JiraServiceFacade.class);
		picoContainer.addComponent(TokenManager.class);
		
		clock = new MockHardwareClock();
		clock.setTime(0);
		picoContainer.addComponent(clock);

		picoContainer.addComponent(JiraImpl.class);
		jira = picoContainer.getComponent(Jira.class);
	}

	private JiraServiceMockFactory _serviceFactory() {
		jiraMockHandler = new JiraMockInvocationHandler();
		JiraServiceMockFactory serviceFactory = new JiraServiceMockFactory(
				jiraMockHandler);
		jiraMockHandler.addResult("login", "token");
		return serviceFactory;
	}

	private JiraOptions _jiraOptions() {
		JiraOptions jiraOptions = new JiraOptions();
		jiraOptions.setURL("https://url.com");
		jiraOptions.setUserName("user");
		jiraOptions.setPassword("password");
		return jiraOptions;
	}

	private void assertServiceLog(String... calls) {
		Assert.assertArrayEquals(calls, jiraMockHandler.getLogAsStrings());
	}

	private void addOneSecond() {
		addToClock(Calendar.SECOND, 1);
	}

	private void setClockOneSecondBeforeTokenTimeout() {
		addToClock(Calendar.MINUTE, 10);
	}

	private void addToClock(int calendarFieldConstant, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(clock.getTime());
		calendar.add(calendarFieldConstant, amount);
		clock.setTime(calendar.getTime());
	}

	private RemoteIssue[] createIssueResult(String issueKey, String issueSummary) {
		RemoteIssue[] issues = new RemoteIssue[] { new RemoteIssue() };
		issues[0].setKey(issueKey);
		issues[0].setSummary(issueSummary);
		return issues;
	}

	@Test
	public void testJiraGetIssueByKey() {
		jiraMockHandler.addResult("getIssuesFromJqlSearch",
				createIssueResult("ISSUE-1", "Issue number one"));

		JiraIssue issue = jira.getIssueByKey("ISSUE-1");
		Assert.assertEquals(issue.getKey(), "ISSUE-1");
		Assert.assertEquals(issue.getSummary(), "Issue number one");
		assertServiceLog("login [user, password]",
				"getIssuesFromJqlSearch [token, key = ISSUE-1, 20]");
	}

	@Test
	public void testJiraGetIssueByKeyWithException() {

		jiraMockHandler.addException("getIssuesFromJqlSearch",
				new RemoteValidationException());

		try {
			jira.getIssueByKey("ISSUE-1");
			Assert.fail();
		} catch (Exception ex) {
			Assert.assertEquals(JiraIssueNotFoundException.class, ex.getClass());
		}

		assertServiceLog("login [user, password]",
				"getIssuesFromJqlSearch [token, key = ISSUE-1, 20]");
	}

	@Test
	public void testGetIssueStatus() {
		RemoteIssue[] issueResult = createIssueResult("ISSUE-1",
				"Issue number one");
		issueResult[0].setStatus("3");
		jiraMockHandler.addResult("getIssueById", issueResult[0]);

		RemoteStatus[] statuses = new RemoteStatus[] { new RemoteStatus() };
		statuses[0].setName("issue-1 status");
		statuses[0].setId("3");
		jiraMockHandler.addResult("getStatuses", statuses);

		JiraIssueData issueData = new JiraIssueData();
		issueData.setKey("ISSUE-1");
		issueData.setSummary("Issue number one");
		issueData.setId("2");
		JiraIssue jiraIssue = new JiraIssue(issueData);

		Assert.assertEquals("issue-1 status", jira.getIssueStatus(jiraIssue));

		assertServiceLog("login [user, password]", "getIssueById [token, 2]",
				"getStatuses [token]");
	}

	@Test
	public void testNewWorklog() {
		jira.newWorklog("ISSUE-1", Calendar.getInstance(), "1h 30m");

		Assert.assertEquals("login [user, password]", jiraMockHandler
				.getMethodCall(0).toString());

		String worklogPattern = "addWorklogAndAutoAdjustRemainingEstimate \\[token, ISSUE-1, com.dolby.jira.net.soap.jira.RemoteWorklog@\\w+\\]";
		MethodCall worklogCall = jiraMockHandler.getMethodCall(1);
		Assert.assertTrue(worklogCall.toString().matches(worklogPattern));

		RemoteWorklog worklog = worklogCall.getArgument(RemoteWorklog.class);
		Assert.assertEquals(worklog.getTimeSpent(), "1h 30m");
	}

	@Test
	public void testTokenCache() {
		jiraMockHandler.addResult("getIssuesFromJqlSearch",
				createIssueResult("NULL", "an issue"));

		jira.getIssueByKey("NULL");
		assertServiceLog("login [user, password]",
				"getIssuesFromJqlSearch [token, key = NULL, 20]");

		setClockOneSecondBeforeTokenTimeout();
		jiraMockHandler.addResult("login", "newtoken");
		jira.getIssueByKey("NULL");
		assertServiceLog("login [user, password]",
				"getIssuesFromJqlSearch [token, key = NULL, 20]",
				"getIssuesFromJqlSearch [token, key = NULL, 20]");

		addOneSecond();
		jira.getIssueByKey("NULL");
		assertServiceLog("login [user, password]",
				"getIssuesFromJqlSearch [token, key = NULL, 20]",
				"getIssuesFromJqlSearch [token, key = NULL, 20]",
				"login [user, password]",
				"getIssuesFromJqlSearch [newtoken, key = NULL, 20]");
	}
}
