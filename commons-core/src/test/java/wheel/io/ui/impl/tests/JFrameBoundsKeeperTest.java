package wheel.io.ui.impl.tests;

import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import junit.framework.TestCase;
import wheel.io.ui.JFrameBoundsKeeper;
import wheel.io.ui.impl.BoundsLoggingPeristence;
import wheel.io.ui.impl.JFrameBoundsKeeperImpl;
import wheel.io.ui.impl.LoggingJFrameBoundsaKeeper;

public class JFrameBoundsKeeperTest extends TestCase {
	
	
	private JFrameBoundsKeeper _frameBoundsKeeper;

	@Override
	protected void setUp() throws Exception {
		final BoundsLoggingPeristence persistence = new BoundsLoggingPeristence(new TransientBoundsPersistence());
		final JFrameBoundsKeeperImpl keeper = new JFrameBoundsKeeperImpl(persistence);
		_frameBoundsKeeper = new LoggingJFrameBoundsaKeeper(keeper);
	}
	
	public void testKeepBounds() throws InterruptedException, InvocationTargetException{
		JFrame testFrame = null;
		JFrame testFrame2 = null;
		JFrame testFrame3 = null;
		
		try {
			final Rectangle bounds = new Rectangle(100,100,200,200);
			testFrame = openTestFrame();
			this.setBounds(testFrame, bounds);
				
			testFrame2 = openTestFrame();
			assertEquals(bounds, testFrame2.getBounds());
			final Rectangle bounds2 = new Rectangle(100,100,300,220);
			this.setBounds(testFrame2, bounds2);
			
			testFrame3 = openTestFrame();
			assertEquals(bounds2, testFrame3.getBounds());

		} finally {
			
			for (final JFrame frame : new JFrame[]{testFrame, testFrame2, testFrame3}){
				if (frame != null)
					frame.setVisible(false);
			}
		}
	}

	private void setBounds(final JFrame testFrame, final Rectangle bounds) throws InterruptedException, InvocationTargetException {
			SwingUtilities.invokeAndWait(new Runnable(){
				@Override
				public void run() {
					testFrame.setBounds(bounds);				
				}

			});
			
	}

	private JFrame openTestFrame() throws InterruptedException, InvocationTargetException {
		
		final JFrame testFrame = new JFrame();
		
		SwingUtilities.invokeAndWait(new Runnable() {
		
			@Override
			public void run() {
				testFrame.setVisible(true);
			}
		});
		
		
		
		final String testFrameId = "testFrame";
		_frameBoundsKeeper.keepBoundsFor(testFrame, testFrameId);
		testFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		return testFrame;
	}

	
}
