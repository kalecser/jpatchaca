package tasks.tasks.tests;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import tasks.ActiveTask;
import tasks.TaskView;
import tasks.TasksListener;
import tasks.home.TasksHomeImpl;
import tasks.tasks.Tasks;
import basic.Subscriber;
import basic.SystemClockImpl;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public class TasksHomeTest {

	private final Mockery context = new JUnit4Mockery();
	private ObjectIdentity oid;
	private String taskName;
	private Double budget;
	private Subscriber subscriberMocker;
	private TasksListener tasksListenerMocker;
	private TasksHomeImpl home;
	private Tasks tasks;
	private SystemClockImpl clock;

	@Before
	public void setUp() {
		oid = new ObjectIdentity("1");
		taskName = "test";
		budget = 10.0;

		subscriberMocker = context.mock(Subscriber.class);
		tasksListenerMocker = context.mock(TasksListener.class);
		tasks = new Tasks();
		clock = new SystemClockImpl();
		home = new TasksHomeImpl(null, tasks, clock, new ActiveTask(), null);
	}

	@Test
	public void testCreateTask() throws MustBeCalledInsideATransaction {

		home.taskListChangedAlert().subscribe(subscriberMocker);
		home.addTasksListener(tasksListenerMocker);

		assertEquals(0, tasks.tasks().size());

		context.checking(new Expectations() {

			{
				oneOf(tasksListenerMocker).createdTask(with(any(TaskView.class)));
				oneOf(subscriberMocker).fire();
			}
		});
		home.createTask(oid, new MockTaskName(taskName), budget);

		assertEquals(1, tasks.tasks().size());
		assertNotNull(tasks.get(oid));
		assertEquals(oid, tasks.idOf((tasks.get(oid))));

	}

	@Test
	public void testRemoveTask() throws MustBeCalledInsideATransaction {

		home.createTask(oid, new MockTaskName(taskName), budget);

		home.taskListChangedAlert().subscribe(subscriberMocker);
		
		context.checking(new Expectations() {

			{
				oneOf(subscriberMocker).fire();
			}
		});
		
		home.remove(tasks.get(oid));

		assertEquals(0, tasks.tasks().size());

	}

}
