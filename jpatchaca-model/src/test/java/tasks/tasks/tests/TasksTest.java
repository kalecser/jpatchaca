package tasks.tasks.tests;

import org.junit.Assert;
import org.junit.Test;

import periodsInTasks.MockTask;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;
import tasks.tasks.Tasks;

public class TasksTest {

	@Test
	public void testTaskAddition() throws MustBeCalledInsideATransaction{
		Tasks tasks = new Tasks();
		MockTask task = new MockTask("test task");
		ObjectIdentity oid = new ObjectIdentity("1");
		tasks.add(oid, task);
		
		Assert.assertTrue(task == tasks.get(oid));
		Assert.assertTrue(task == tasks.byName("test task").unbox());
		Assert.assertTrue(oid == tasks.idOf(task));
		Assert.assertEquals(task.name(), tasks.taskNames().get(0));
	}
	
}
