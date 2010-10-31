package tasks.persistence;

import labels.labels.SelectedLabel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import periodsInTasks.MockTask;
import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;
import tasks.home.TaskData;
import tasks.tasks.Tasks;
import basic.NonEmptyString;
import core.ObjectIdentity;
import events.StartTaskEvent3;
import events.persistence.MustBeCalledInsideATransaction;

public class StartTaskPersistenceTest {

	private StartTaskDelegate delegate;
	private MockEventsConsumer consumer;
	private Tasks tasks;
	private MockCreateTaskDelegate createTask;
	private SelectedLabel selectedLabel;

	@Before
	public void setup() {
		delegate = new StartTaskDelegate();
		consumer = new MockEventsConsumer();
		tasks = new Tasks();
		createTask = new MockCreateTaskDelegate(null);
		selectedLabel = new SelectedLabel();
		final StartTaskPersistence persistence = new StartTaskPersistence(
				consumer, delegate, tasks, createTask, selectedLabel);
		persistence.start();
	}

	@Test
	public void testStartExistingTask() throws MustBeCalledInsideATransaction {

		tasks.add(new ObjectIdentity("1"), new MockTask("foo"));

		delegate.starTask(new StartTaskData(new TaskData(new NonEmptyString("foo")), 42));
		final StartTaskEvent3 event = (StartTaskEvent3) (consumer.popEvent());

		Assert.assertTrue(!createTask.taskCreated());
		Assert.assertEquals("foo", event.getName().unbox());
		Assert.assertEquals(42, event.getMillisecondsAgo());
	}

	@Test
	public void testStartNewTask() throws MustBeCalledInsideATransaction {

		selectedLabel.update("foobar");
		delegate.starTask(new StartTaskData(new TaskData(new NonEmptyString("foo")), 42));
		
		final StartTaskEvent3 event = (StartTaskEvent3) (consumer.popEvent());
		Assert.assertTrue(createTask.taskCreated());
		Assert.assertEquals("foo", createTask.taskName());
		Assert.assertEquals("foobar", createTask.labelName());
		Assert.assertEquals("foo", event.getName().unbox());
		Assert.assertEquals(42, event.getMillisecondsAgo());
		
		
		
	}

}
