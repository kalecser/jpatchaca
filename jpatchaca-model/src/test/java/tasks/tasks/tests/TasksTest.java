package tasks.tasks.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import periodsInTasks.MockTask;
import tasks.tasks.Tasks;
import basic.NonEmptyString;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public class TasksTest {

	@Test
	public void testTaskAddition() throws MustBeCalledInsideATransaction {
		final Tasks tasks = new Tasks();
		final MockTask task = new MockTask("test task");
		final ObjectIdentity oid = new ObjectIdentity("1");
		tasks.add(oid, task);

		Assert.assertTrue(task == tasks.get(oid));
		Assert.assertTrue(task == tasks.byName(new NonEmptyString("test task"))
				.unbox());
		Assert.assertTrue(oid == tasks.idOf(task));
		Assert.assertEquals(task.name(), tasks.taskNames().get(0));
	}

	@Test
	public void testTaskNames() throws MustBeCalledInsideATransaction {
		final Tasks tasks = new Tasks();

		final MockTask mockTask = new MockTask("test task");
		tasks.add(new ObjectIdentity("1"), mockTask);

		final List<String> names = tasks.taskNames();
		mockTask.setName(new MockTaskName("test 1"));
		Assert.assertEquals(1, names.size());
		Assert.assertEquals("test 1", names.get(0));

	}

	@Test
	public void testTaskRemoval() throws MustBeCalledInsideATransaction {
		final Tasks tasks = new Tasks();

		final MockTask mockTask = new MockTask("test task");
		tasks.add(new ObjectIdentity("1"), mockTask);
		tasks.remove(mockTask);

		Assert.assertEquals(0, tasks.taskNames().size());

	}

}
