package wheel.io.ui;

import javax.swing.JFrame;

public interface JFrameBoundsKeeper {

	public abstract void keepBoundsFor(final JFrame frame, final String id);

}