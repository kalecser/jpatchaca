package tasks.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import periodsInTasks.MockTask;
import tasks.ActiveTask;
import tasks.processors.StartTaskProcessor3;
import tasks.tasks.Tasks;
import basic.NonEmptyString;
import core.ObjectIdentity;
import events.StartTaskEvent3;
import events.persistence.MustBeCalledInsideATransaction;

public class StartTaskProcessorTest {

	private Tasks tasks;
	private ActiveTask activeTask;
	private StartTaskProcessor3 processor;

	@Before
	public void setUp() throws MustBeCalledInsideATransaction {
		tasks = new Tasks();
		activeTask = new ActiveTask();
		processor = new StartTaskProcessor3(tasks, activeTask);

		createTask("1", "test");
		createTask("2", "test 2");
	}

	@Test
	public void testStartTask() throws MustBeCalledInsideATransaction {
		startTask("test", 42);

		assertActiveTask("test", 42);
	}

	@Test
	public void testStartTaskWithActiveTask()
			throws MustBeCalledInsideATransaction {
		startTask("test", 0);
		startTask("test 2", 0);

		assertActiveTask("test 2", 0);
		taskByName("test").assertStoppedMillisecondsAgo(0);

	}

	@Test
	public void testStartTaskMillisecondsAgoWithActiveTask()
			throws MustBeCalledInsideATransaction {
		startTask("test", 0);
		startTask("test 2", 42);

		assertActiveTask("test 2", 42);
		taskByName("test").assertStoppedMillisecondsAgo(42);

	}

	private void createTask(final String id, final String name)
			throws MustBeCalledInsideATransaction {
		tasks.add(new ObjectIdentity(id), new MockTask(name));
	}

	private MockTask taskByName(final String name) {
		final MockTask testTask = (MockTask) tasks.byName(
				new NonEmptyString(name)).unbox();
		return testTask;
	}

	private void startTask(final String taskName, final long millisPerMinute)
			throws MustBeCalledInsideATransaction {
		processor.execute(new StartTaskEvent3(new NonEmptyString(taskName),
				millisPerMinute));
	}

	private void assertActiveTask(final String activeTaskName, final long millis) {
		final MockTask taskView = (MockTask) activeTask.currentValue().unbox();
		Assert.assertEquals(activeTaskName, taskView.name());
		taskView.assertWasStarted(millis);
	}

}
