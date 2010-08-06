package tasks;

import org.junit.Assert;
import org.junit.Test;

import periodsInTasks.MockTask;

public class TasksByNameComparatorTest {

	@Test
	public void testTaskByNameComparator() {
		assertComparison("e", "E", 0);
		assertComparison("a", "z", -1);
	}

	private void assertComparison(final String name1, final String name2,
			final int result) {
		Assert.assertEquals(result, new TasksByNameComparator().compare(
				new MockTask(name1), new MockTask(name2)));
	}
}
