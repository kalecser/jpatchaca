/*
 * Created on 14/04/2009
 */
package ui.swing.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang.UnhandledException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UIEventsExecutorImplTest {

	final class ExpensiveCommand implements Runnable {

		private final long millis;

		public ExpensiveCommand(final long millis) {
			this.millis = millis;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(millis);
			} catch (final InterruptedException e) {
				throw new UnhandledException(e);
			}
			UIEventsExecutorImplTest.this.executed.add(Long.valueOf(millis));
		}

	}

	List<Long> executed;

	@Before
	public void beforeTest() {
		this.executed = new ArrayList<Long>(3);
	}

	@Test
	public void testExecute() throws InterruptedException, ExecutionException {
		final long[] times = { 100, 500, 25, 250, 50 };
		final List<Long> expected = new ArrayList<Long>(times.length);
		final List<FutureTask<Object>> commands = new ArrayList<FutureTask<Object>>(
				times.length);
		for (final Long millis : times) {
			expected.add(millis);
			commands.add(newExpensiveCommand(millis));
		}
		final UIEventsExecutorImpl executor = new UIEventsExecutorImpl(null);
		enqueueEveryone(commands, executor);
		waitForEveryoneToFinish(commands);
		Assert.assertEquals(expected, this.executed);
	}

	private void waitForEveryoneToFinish(
			final Collection< ? extends Future< ? >> futures)
			throws InterruptedException, ExecutionException {
		for (final Future< ? > future : futures) {
			future.get();
		}
	}

	private void enqueueEveryone(
			final Collection< ? extends Runnable> commands,
			final UIEventsExecutorImpl executor) {
		for (final Runnable command : commands) {
			executor.execute(command);
		}
	}

	private FutureTask<Object> newExpensiveCommand(final long millis) {
		return new FutureTask<Object>(new ExpensiveCommand(millis), null);
	}

}
