package tasks.persistence;

import org.junit.Assert;
import org.junit.Test;

import tasks.delegates.CreateTaskDelegate;
import tasks.tasks.TaskData;
import basic.IdProvider;
import basic.NonEmptyString;
import core.ObjectIdentity;
import events.CreateTaskEvent3;

public class CreateTaskPersistenceTest {

	
	@Test
	public void testCreateTaskPersistence(){
		CreateTaskDelegate delegate = new CreateTaskDelegate();
		MockEventsConsumer consumer = new MockEventsConsumer();
		IdProvider mockIdProvider = new IdProvider() {
		
			@Override
			public ObjectIdentity provideId() {
				return new ObjectIdentity("1");
			}
		};
		
		CreateTaskPersistence persistence = new CreateTaskPersistence(delegate, consumer, mockIdProvider);
		persistence.start();
		
		
		NonEmptyString taskName = new NonEmptyString("foo");
		double budget = 0.0;
		delegate.createTask(new TaskData(taskName, budget));
		
		CreateTaskEvent3 event3 = (CreateTaskEvent3)consumer.getEvent();
		Assert.assertEquals(taskName.unbox(), event3.getTaskName());
		Assert.assertEquals(new Double(budget), event3.getBudget());
		Assert.assertEquals(new ObjectIdentity("1"), event3.getObjectIdentity());
	}
}
