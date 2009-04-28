package tasks.taskName;

import org.junit.Assert;
import org.junit.Test;

import tasks.tasks.taskName.TaskNameFactory;

public class TaskNameFactoryTest {

	@Test
	public void testTaskNameFactory() {
		final TaskNameFactory factory = new TaskNameFactory();
		Assert.assertEquals("foo", factory.createTaskname("foo").unbox());
		Assert.assertEquals("foo_new", factory.createTaskname("foo").unbox());
		Assert.assertEquals("null_named_task", factory.createTaskname(null)
				.unbox());
		Assert.assertEquals("null_named_task_new", factory.createTaskname(null)
				.unbox());
		Assert.assertEquals("empty_named_task", factory.createTaskname("")
				.unbox());
		Assert.assertEquals("empty_named_task_new", factory.createTaskname("")
				.unbox());
	}

}
