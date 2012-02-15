package tasks.persistence;

import static org.junit.Assert.fail;
import jira.events.JiraEventFactory;
import jira.events.SetJiraIssueToTask;
import jira.exception.JiraIssueNotWorkableException;
import jira.issue.JiraIssue;
import jira.issue.JiraIssueData;
import jira.service.Jira;
import jira.service.JiraMock;
import labels.labels.LabelsHome;
import labels.labels.SelectedLabel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.behaviors.Caching;

import tasks.delegates.CreateTaskDelegateImpl;
import tasks.delegates.CreateTaskDelegate;
import tasks.home.TaskData;
import basic.NonEmptyString;
import basic.mock.MockIdProvider;
import core.ObjectIdentity;
import events.CreateTaskEvent3;

public class CreateTaskPersistenceTest {

	private CreateTaskDelegate delegate;
	private MockEventsConsumer consumer;
	private JiraMock jira;

	@Before
	public void setup() {
		DefaultPicoContainer container = new DefaultPicoContainer(new Caching());
		container.addComponent(SelectedLabel.class);
		container.addComponent(CreateTaskDelegateImpl.class);
		container.addComponent(MockIdProvider.class);
		container.addComponent(JiraMock.class);
		container.addComponent(JiraEventFactory.class);

		container.addComponent(CreateTaskPersistence.class);
		container.addComponent(MockEventsConsumer.class);
		container.start();

		delegate = container.getComponent(CreateTaskDelegateImpl.class);
		consumer = container.getComponent(MockEventsConsumer.class);
		jira = (JiraMock) container.getComponent(Jira.class);
	}

	@Test
	public void testCreateTaskPersistence() {

		TaskData taskData = taskData();
		delegate.createTask(taskData);

		final CreateTaskEvent3 event3 = (CreateTaskEvent3) consumer.popEvent();
		Assert.assertEquals("foo", event3.getTaskName());
		Assert.assertEquals(new Double(0.0), event3.getBudget());
		Assert.assertEquals(new ObjectIdentity("0"), event3.getObjectIdentity());
		
		final SetJiraIssueToTask issueEvent = (SetJiraIssueToTask) consumer.popEvent();
		JiraIssue issue = issueEvent.getJiraIssueId();
		Assert.assertEquals("JPT-1", issue.getKey());
		Assert.assertEquals("Test Issue", issue.getSummary());
	}

	@Test
	public void testNotWorkableIssueDoNotCreateTask() {
		jira.setIssueWorkable(false);
		try {
			TaskData taskData = taskData();
			delegate.createTask(taskData);
			fail();
		} catch (JiraIssueNotWorkableException ex) {
		}

		Assert.assertTrue(consumer.events().isEmpty());
	}

	private TaskData taskData() {
		NonEmptyString taskName = new NonEmptyString("foo");
		TaskData taskData = new TaskData(taskName);
		taskData.setBudget(0.0);
		taskData.setLabel(LabelsHome.ALL_LABEL_NAME);

		JiraIssueData issueData = new JiraIssueData();
		issueData.setKey("JPT-1");
		issueData.setSummary("Test Issue");
		taskData.setJiraIssue(new JiraIssue(issueData));
		return taskData;
	}

}
