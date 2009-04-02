package basic;

import java.util.concurrent.atomic.AtomicBoolean;

public class DeferredExecutor{

	private static boolean synchronous = false;
	private final AtomicBoolean running = new AtomicBoolean(true);
	protected final AtomicBoolean waitingToRun = new AtomicBoolean(false);
	private final AtomicBoolean wasCalled= new AtomicBoolean(false);
	private final Runnable runnable;
	private final int milliseconds;

	private Thread thread;


	public DeferredExecutor(final int milliseconds, Runnable runnable) {
		this.milliseconds = milliseconds;
		this.runnable = runnable;
	}

	public synchronized void execute() {
		
		if (synchronous){
			runnable.run();
			return;
		}
		
		wasCalled.set(true);

		if (  running.getAndSet(false) && (!waitingToRun.get())){
				thread = new Thread(){
					@Override
					public void run() {
						try {
							while (wasCalled.getAndSet(false))
								Thread.sleep(milliseconds);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						waitingToRun.set(true);
						synchronized (runnable) {
							waitingToRun.set(false);
							running.set(true);
							runnable.run();
						}	
					}
				};
				thread.start();
			}
		
	}
	
	public static void makeSynchronous(){
		synchronous = true;
	}
	
}





