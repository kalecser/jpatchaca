package tasks.taskName;

import org.junit.Assert;
import org.junit.Test;


public class TaskNameFactoryTest {

	@Test
	public void testTaskNameFactory() {
		final MockTaskNames names = new MockTaskNames();
		final TaskNameFactory factory = new TaskNameFactory(names);

		Assert.assertEquals("foo", factory.createTaskname("foo").unbox());

		names.add("foo");
		Assert.assertEquals("foo_new", factory.createTaskname("foo").unbox());

		Assert.assertEquals("null_named_task", factory.createTaskname(null)
				.unbox());

		names.add("null_named_task");
		Assert.assertEquals("null_named_task_new", factory.createTaskname(null)
				.unbox());

		Assert.assertEquals("empty_named_task", factory.createTaskname("")
				.unbox());

		names.add("empty_named_task");
		Assert.assertEquals("empty_named_task_new", factory.createTaskname("")
				.unbox());
	}

}
