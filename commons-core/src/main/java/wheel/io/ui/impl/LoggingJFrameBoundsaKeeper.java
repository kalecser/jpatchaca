package wheel.io.ui.impl;

import javax.swing.JFrame;

import wheel.io.ui.JFrameBoundsKeeper;

public class LoggingJFrameBoundsaKeeper implements JFrameBoundsKeeper {

	private final JFrameBoundsKeeper keeper;

	public LoggingJFrameBoundsaKeeper(final JFrameBoundsKeeper keeper){
		this.keeper = keeper;
		
	}
	
	@Override
	public void keepBoundsFor(final JFrame frame, final String id) {
		JFrameBoundsKeeperLogger.getLogger().debug("Keepeing bounds for " + id + "::" +  frame.getBounds());
		keeper.keepBoundsFor(frame, id);
	}

}
