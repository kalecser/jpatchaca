package basic;

import java.util.concurrent.atomic.AtomicBoolean;



/**
 * @author kalecser
 *
 * An Object whose purpose is to avoid multiple executions of self-annulling actions. 
 * 
 * It will receive two parameters: a runnable and an amount of milliseconds. 
 * The runnable's run method will be called as a consequence of execute method in at least 
 * x milliseconds being x the amount specified by user;
 * If the execute method is called again before x milliseconds have passed the 
 * runnable run method invocation will be postponed by x milliseconds.  
 *   
 * Example:

		//creating DefferedExecutor with 1 second delay
		DeferredExecutor executor = new DeferredExecutor(1000, new Runnable(){
		@Override
		public void run() {
			System.out.println("foo");
		}
		});
		
		
		executor.execute();
		executor.execute();
		executor.execute();
		executor.execute();
		//will print foo only once.
		Thread.sleep(3000);
		
		
		 
 */
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





