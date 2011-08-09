package tasks.tasks;

import jira.JiraOptions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDataParser;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.mock.MockJira;
import ui.swing.tasks.StartTaskScreenModelImpl;
import basic.Delegate.Listener;

public class StartTaskScreenModelTest {


	@Test
	public void testStartTaskModelWithoutJiraIntegration(){
		
		startTask("[patchaca-123]");
		Assert.assertEquals("[patchaca-123]", startedTask());
		
	}
	@Test
	public void testStartTaskModelWithJiraIntegration(){
		
		turnJiraIntegrationOn();
		addIssueCodeDescription("patchaca-123", "fixing bug");
		startTask("[patchaca-123]");
		Assert.assertEquals("[patchaca-123] fixing bug", startedTask());
		
	}
	
	@Test
	public void testStartTaskInvalidIssue(){
		
		turnJiraIntegrationOn();
		addIssueCodeDescription("patchaca-123", "fixing bug");
		startTask("[patchaca-1234]");
		Assert.assertEquals("[patchaca-1234]", startedTask());
		
	}
	

	private final StartTaskDelegate startTaskDelegate = new StartTaskDelegate();
	private final TasksView tasks = new Tasks();
	private final StartTaskDataParser parser = new StartTaskDataParser();
	JiraOptions options = new JiraOptions();
	private final MockJira jira = new MockJira(options);	
	private final StartTaskScreenModelImpl subject = new StartTaskScreenModelImpl(startTaskDelegate, tasks, parser, jira, options );
	protected String startedTask;
	
	@Before
	public void setup(){
		startTaskDelegate.addListener(new Listener<StartTaskData>() {
			@Override
			public void execute(StartTaskData object) {
				startedTask = object.taskData().getTaskName();
			}
		});
	}
	
	private void addIssueCodeDescription(String code, String description) {
		jira.addIssueKeyDescription(code, description);
		
	}

	private void turnJiraIntegrationOn() {
		options.setPassword("foo");
		
	}

	private String startedTask() {
		return startedTask;
	}

	private void startTask(String taskName) {
		subject.startTask(taskName);
		
	}
}
