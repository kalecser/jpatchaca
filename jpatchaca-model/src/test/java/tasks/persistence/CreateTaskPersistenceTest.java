package tasks.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tasks.delegates.CreateTaskDelegate;
import tasks.home.TaskData;
import basic.IdProvider;
import basic.NonEmptyString;
import basic.mock.MockIdProvider;
import core.ObjectIdentity;
import events.CreateTaskEvent3;

public class CreateTaskPersistenceTest {

	private CreateTaskDelegate delegate;
	private MockEventsConsumer consumer;

	@Before
	public void setup() {
		delegate = new CreateTaskDelegate();
		consumer = new MockEventsConsumer();
		final IdProvider mockIdProvider = new MockIdProvider();

		final CreateTaskPersistence persistence = new CreateTaskPersistence(
				delegate, consumer, mockIdProvider);
		persistence.start();
	}

	@Test
	public void testCreateTaskPersistence() {

		final NonEmptyString taskName = new NonEmptyString("foo");
		final double budget = 0.0;
		delegate.createTask(new TaskData(taskName, budget));

		final CreateTaskEvent3 event3 = (CreateTaskEvent3) consumer.lastEvent();
		Assert.assertEquals(taskName.unbox(), event3.getTaskName());
		Assert.assertEquals(new Double(budget), event3.getBudget());
		Assert
				.assertEquals(new ObjectIdentity("0"), event3
						.getObjectIdentity());
	}
}
