package wheel.io.ui.impl;

import java.awt.Rectangle;

import wheel.io.ui.BoundsPersistence;

public class BoundsLoggingPeristence implements BoundsPersistence {

	private final BoundsPersistence persistence;

	public Rectangle getStoredBounds(final String id) {
		final Rectangle storedBounds = persistence.getStoredBounds(id);
		JFrameBoundsKeeperLogger.getLogger().debug(" Reestoring " + storedBounds + " to " + id);
		return storedBounds;
	}

	public void setBounds(final String id, final Rectangle bounds) {
		JFrameBoundsKeeperLogger.getLogger().debug(" Storing " + bounds + " to " + id);
		persistence.setBounds(id, bounds);
	}

	public void store() {
		JFrameBoundsKeeperLogger.getLogger().debug("Storing to medium");
		persistence.store();
	}

	public BoundsLoggingPeristence(final BoundsPersistence persistence){
		this.persistence = persistence;
		
	}

}
