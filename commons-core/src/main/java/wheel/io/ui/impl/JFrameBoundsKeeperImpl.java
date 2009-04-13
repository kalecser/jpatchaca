package wheel.io.ui.impl;

import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import wheel.io.Log;
import wheel.io.ui.BoundsPersistence;
import wheel.io.ui.JFrameBoundsKeeper;

public class JFrameBoundsKeeperImpl implements JFrameBoundsKeeper {

	private final BoundsPersistence _persistence;
	
	public JFrameBoundsKeeperImpl(final BoundsPersistence persistence) {
		_persistence = persistence;
	}
	
	public void keepBoundsFor(final JFrame frame, final String id){
		try {
			
			if (SwingUtilities.isEventDispatchThread()){
				keepBoundsRunnable(frame, id).run();
			} else {
			
			SwingUtilities.invokeAndWait(keepBoundsRunnable(frame, id));
		}
		} catch (final InterruptedException e) {
			Log.log(e);
		} catch (final InvocationTargetException e) {
			Log.log(e);
		}
	}

	private Runnable keepBoundsRunnable(final JFrame frame, final String id) {
		return new Runnable() {
		
			@Override
			public void run() {
				restorePreviousBounds(id, frame);
				startKeepingBounds(id, frame);		
			}
		
		};
	}

	private void restorePreviousBounds(final String id, final JFrame frame) {
		
		final Rectangle storedBounds = _persistence.getStoredBounds(id);
		if (storedBounds != null){
			//ugly!
			final long currentTime = System.currentTimeMillis();		
			final int timeoutWaitingForJFrameToStoreBounds = 3000;
			while(!frame.getBounds().equals(storedBounds) && currentTime + timeoutWaitingForJFrameToStoreBounds > System.currentTimeMillis()){
				frame.setLocation(storedBounds.getLocation());
				frame.setPreferredSize(storedBounds.getSize());
				frame.setSize(storedBounds.getSize());
			}
			
			
		}
	}

	private void startKeepingBounds(final String id, final JFrame frame) {
		
		frame.addComponentListener(new ComponentAdapter() {
		
			@Override
			public void componentResized(final ComponentEvent e) {
				boundsChanged(e);
			}

			private void boundsChanged(final ComponentEvent e) {
				final Rectangle bounds = frame.getBounds();
				if (_persistence.getStoredBounds(id) == bounds)
					return;
				
				_persistence.setBounds(id, bounds);
				_persistence.store();
			}
		
			@Override
			public void componentMoved(final ComponentEvent e) {
				boundsChanged(e);
			}
		
		});
	}
	
	
	

}
