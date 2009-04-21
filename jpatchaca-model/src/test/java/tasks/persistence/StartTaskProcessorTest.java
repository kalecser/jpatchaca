package tasks.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import periodsInTasks.MockTask;
import tasks.ActiveTask;
import tasks.processors.StartTaskProcessor2;
import tasks.tasks.Tasks;
import basic.NonEmptyString;
import core.ObjectIdentity;
import events.StartTaskEvent2;
import events.persistence.MustBeCalledInsideATransaction;

public class StartTaskProcessorTest {

	private Tasks tasks;
	private ActiveTask activeTask;
	private StartTaskProcessor2 processor;

	@Before
	public void setUp() {
		tasks = new Tasks();
		activeTask = new ActiveTask();
		processor = new StartTaskProcessor2(tasks, activeTask);
	}

	@Test
	public void testStartTask() throws MustBeCalledInsideATransaction {
		tasks.add(new ObjectIdentity("1"), new MockTask("test"));
		processor.execute(new StartTaskEvent2(new NonEmptyString("test"), 42));

		assertActiveTask("test", 42);
	}

	@Test
	public void testStartTaskWithActiveTask()
			throws MustBeCalledInsideATransaction {
		tasks.add(new ObjectIdentity("1"), new MockTask("test"));
		tasks.add(new ObjectIdentity("2"), new MockTask("test 2"));

		processor.execute(new StartTaskEvent2(new NonEmptyString("test"), 0));
		processor.execute(new StartTaskEvent2(new NonEmptyString("test 2"), 0));

		assertActiveTask("test 2", 0);
		final MockTask testTask = (MockTask) tasks.byName(
				new NonEmptyString("test")).unbox();
		testTask.assertStopped();

	}

	private void assertActiveTask(final String activeTaskName,
			final int millisecondsAgo) {
		final MockTask taskView = (MockTask) activeTask.currentValue().unbox();
		Assert.assertEquals(activeTaskName, taskView.name());
		taskView.assertWasStarted(millisecondsAgo);
	}

}
