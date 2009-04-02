package tasks.tasks.tests;


import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import tasks.TasksListener;
import tasks.tasks.TasksHomeImpl;
import basic.Subscriber;
import basic.mock.MockBasicSystem;
import core.ObjectIdentity;

public class TasksHomeTest extends MockObjectTestCase {

	
	private ObjectIdentity oid;
	private String taskName;
	private Double budget;
	private Mock subscriberMocker;
	private Mock tasksListenerMocker;
	private TasksHomeImpl home;

	public void setUp(){
		oid = new ObjectIdentity("1");
		taskName = "test";
		budget = 10.0;
		
		subscriberMocker = mock(Subscriber.class);
		tasksListenerMocker = mock(TasksListener.class);
		home = new TasksHomeImpl(null, new MockBasicSystem());
	}
	
	
	public void testCreateTask() {
		
		
		home.taskListChangedAlert().subscribe((Subscriber) subscriberMocker.proxy());
		home.addTasksListener((TasksListener) tasksListenerMocker.proxy());
		
		assertEquals(0, home.tasks().size());
		
		tasksListenerMocker.expects(once()).method("createdTask");
		subscriberMocker.expects(once()).method("fire");
		home.createTask(oid, taskName, budget);
		
		assertEquals(1, home.tasks().size());
		assertNotNull(home.getTask(oid));
		assertEquals(oid, home.getIdOfTask(home.getTask(oid)));

	}
	
	public void testRemoveTask() {

		home.createTask(oid, taskName, budget);
		
		home.taskListChangedAlert().subscribe((Subscriber) subscriberMocker.proxy());
		subscriberMocker.expects(once()).method("fire");
		home.remove(home.getTask(oid));
		
		assertEquals(0, home.tasks().size());

	}
	
}
