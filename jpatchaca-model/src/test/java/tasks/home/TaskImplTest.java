package tasks.home;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import periods.impl.PeriodManagerImpl;
import periods.impl.PeriodsFactoryImpl;
import tasks.tasks.tests.MockTaskName;
import basic.SystemClock;
import basic.SystemClockImpl;

public class TaskImplTest {

	private SystemClock systemClock;
	private TaskImpl task;

	@Before
	public void setup() {
		systemClock = new SystemClockImpl();
		task = new TaskImpl(new MockTaskName("foo"), systemClock, 0.0,
				new PeriodManagerImpl(), new PeriodsFactoryImpl());
	}

	@Test
	public void testStopTask() {
		setTime(0l);
		task.start();
		setTime(42l);
		task.stop();

		Assert.assertEquals(task.totalTimeInMillis(), 42l);

	}

	@Test
	public void testStopBeforeStarted() {
		setTime(0l);
		task.start();
		setTime(42l);
		task.stop(43l);

		Assert.assertEquals(task.totalTimeInMillis(), 0l);
	}

	private void setTime(final long time) {
		systemClock.setTime(time);
	}
}
