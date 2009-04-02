package wheel.io.ui.impl;

import java.awt.Rectangle;

import wheel.io.files.Directory;
import wheel.io.ui.BoundsPersistence;

public class DeferredDirectoryBoundPersistence implements BoundsPersistence {

	private final DeferredBoundPersistence _persistence;

	public DeferredDirectoryBoundPersistence(Directory directory){
		_persistence = new DeferredBoundPersistence(new DirectoryBoundsPersistence(directory));
	}
	
	@Override
	public Rectangle getStoredBounds(String id) {
		return _persistence.getStoredBounds(id);
	}

	@Override
	public void setBounds(String id, Rectangle bounds) {
		_persistence.setBounds(id, bounds);
		
	}

	@Override
	public void store() {
		_persistence.store();		
	}
	
	@Override
	protected void finalize() throws Throwable {
		_persistence.dispose();
	}

}
