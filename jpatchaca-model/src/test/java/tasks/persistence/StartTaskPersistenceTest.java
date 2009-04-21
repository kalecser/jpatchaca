package tasks.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import periodsInTasks.MockTask;
import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.Tasks;
import basic.NonEmptyString;
import core.ObjectIdentity;
import events.StartTaskEvent2;
import events.persistence.MustBeCalledInsideATransaction;

public class StartTaskPersistenceTest {

	private StartTaskDelegate delegate;
	private MockEventsConsumer consumer;
	private Tasks tasks;
	private MockCreateTaskDelegate createTask;

	@Before
	public void setup() {
		delegate = new StartTaskDelegate();
		consumer = new MockEventsConsumer();
		tasks = new Tasks();
		createTask = new MockCreateTaskDelegate();
		final StartTaskPersistence persistence = new StartTaskPersistence(
				consumer, delegate, tasks, createTask);
		persistence.start();
	}

	@Test
	public void testStartExistingTask() throws MustBeCalledInsideATransaction {

		tasks.add(new ObjectIdentity("1"), new MockTask("foo"));

		delegate.starTask(new StartTaskData(new NonEmptyString("foo"), 42));
		final StartTaskEvent2 event = (StartTaskEvent2) (consumer.lastEvent());

		Assert.assertTrue(!createTask.taskCreated());
		Assert.assertEquals("foo", event.getName().unbox());
		Assert.assertEquals(42, event.getMillisecondsAgo());
	}

	@Test
	public void testStartNewTask() throws MustBeCalledInsideATransaction {

		delegate.starTask(new StartTaskData(new NonEmptyString("foo"), 42));
		final StartTaskEvent2 event = (StartTaskEvent2) (consumer.lastEvent());

		Assert.assertTrue(createTask.taskCreated());
		Assert.assertEquals("foo", createTask.taskName());
		Assert.assertEquals("foo", event.getName().unbox());
		Assert.assertEquals(42, event.getMillisecondsAgo());
	}

}
