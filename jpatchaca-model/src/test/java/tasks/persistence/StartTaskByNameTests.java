package tasks.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import periodsInTasks.MockTask;
import tasks.delegates.CreateTaskDelegate;
import tasks.delegates.StartTaskByNameDelegate;
import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TaskData;
import tasks.tasks.Tasks;
import ui.swing.mainScreen.Delegate;
import basic.NonEmptyString;
import events.persistence.MustBeCalledInsideATransaction;

public class StartTaskByNameTests {

	private StartTaskByNameDelegate startTaskByNameDelegate;
	private LoggingListener<StartTaskData> startedTasks = new LoggingListener<StartTaskData>();
	private LoggingListener<TaskData> createdTasks = new LoggingListener<TaskData>();

	@Before
	public void setup(){
		
		final Tasks tasks = new Tasks();		
		StartTaskDelegate startTaskDelegate = new StartTaskDelegate();
		startTaskByNameDelegate = new StartTaskByNameDelegate();
		CreateTaskDelegate createTask = new CreateTaskDelegate();
		StartTaskByNamePersistence startTaskByNamePersistence = 
			new StartTaskByNamePersistence(startTaskDelegate, startTaskByNameDelegate, tasks, createTask);
		startTaskByNamePersistence.start();
		startTaskDelegate.addListener(startedTasks);
		createTask.addListener(createdTasks);
		createTask.addListener(new Delegate.Listener<TaskData>() {
			@Override
			public void execute(TaskData object) {
				try {
					tasks.add(null, new MockTask(object.getTaskName()));
				} catch (MustBeCalledInsideATransaction e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	@Test
	public void testStartExistingTaskByname() throws MustBeCalledInsideATransaction{
		createdTasks.execute(new TaskData(new NonEmptyString("test"), 0.0));
		startTaskByNameDelegate.starTask(new NonEmptyString("test"));
		Assert.assertEquals("test", startedTasks.events.get(0).taskName().unbox());
		Assert.assertEquals(new Integer(0), startedTasks.events.get(0).millisecondsAgo());
	}
	
	@Test
	public void testStartNewTaskByname() throws MustBeCalledInsideATransaction{
		startTaskByNameDelegate.starTask(new NonEmptyString("test"));
		Assert.assertEquals("test", createdTasks.events.get(0).getTaskName());
		Assert.assertEquals("test", startedTasks.events.get(0).taskName().unbox());
	}
	
	@Test
	public void testStartNewTaskByNameXMinutesAgo() throws MustBeCalledInsideATransaction{
		startTaskByNameDelegate.starTask(new NonEmptyString("test 30 minutes ago"));
		Assert.assertEquals("test", startedTasks.events.get(0).taskName().unbox());
		Integer halfAnHour = new Integer((int) (30 * org.apache.commons.lang.time.DateUtils.MILLIS_PER_MINUTE));
		Assert.assertEquals(halfAnHour, startedTasks.events.get(0).millisecondsAgo());
	}
	
}
