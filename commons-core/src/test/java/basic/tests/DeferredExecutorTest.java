package basic.tests;

import org.junit.Assert;
import org.junit.Test;

import basic.DeferredExecutor;


public class DeferredExecutorTest {

	@Test
	public void testDeferredAction() throws InterruptedException{
		final MockRunnable runnable = new MockRunnable();
		final DeferredExecutor executor = new DeferredExecutor(100, runnable);
		
		executor.execute();
		executor.execute();
		
		synchronized (runnable){
			runnable.wait(300);
		}
		
		Assert.assertTrue(runnable.ran);
		
	}
	
	public static class MockRunnable implements Runnable{

		public boolean ran = false;

		@Override
		public synchronized void run() {
			if (ran)
				throw new IllegalStateException("Trying to run again.");
			ran = true;
			
			synchronized (this) {
				this.notify();
			}
		}
		
	}
	
}
