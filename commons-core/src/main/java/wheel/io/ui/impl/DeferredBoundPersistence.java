package wheel.io.ui.impl;

import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicBoolean;

import wheel.io.ui.BoundsPersistence;
import wheel.lang.Threads;

public final class DeferredBoundPersistence implements BoundsPersistence {

	private final BoundsPersistence _persistence;
	
	private final Thread _boundsKeeperThread;
	private final AtomicBoolean _isDirty;
	
	public DeferredBoundPersistence(BoundsPersistence persistence){
		_persistence = persistence;		
		_isDirty = new AtomicBoolean(false);
		_boundsKeeperThread = new Thread(new PersistBounds());
		_boundsKeeperThread.start();
	}
	
	
	final class PersistBounds implements Runnable {


		@Override
		public void run() {
			
			while(true){ 
			
				if (_isDirty.getAndSet(false))
					deferredWriteBoundsToDisk();
				
				synchronized (_isDirty){
					if (!_isDirty.get())
						try {
							_isDirty.wait();
						} catch (InterruptedException e) {
							break;
						}
				}
			
			}
				
		}

		private void deferredWriteBoundsToDisk() {
			final int diskWriteThreshold = 1000;
			Threads.sleepWithoutInterruptions(diskWriteThreshold);
			if (_isDirty.getAndSet(false)){
				deferredWriteBoundsToDisk();
				return;
			}
			
			_persistence.store();				
		}

	}


	@Override
	public synchronized Rectangle getStoredBounds(String id) {
		return _persistence.getStoredBounds(id);
	}

	@Override
	public synchronized void setBounds(String id, Rectangle bounds) {
		_persistence.setBounds(id, bounds);
	}

	@Override
	public synchronized void store() {
		synchronized (_isDirty) {
			_isDirty.set(true);
			_isDirty.notify();			
		}		
	}

	public void dispose() {
		_boundsKeeperThread.interrupt();	
	}

}
