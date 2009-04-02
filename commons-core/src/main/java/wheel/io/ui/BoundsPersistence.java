package wheel.io.ui;

import java.awt.Rectangle;

public interface BoundsPersistence {

	Rectangle getStoredBounds(String id);
	void setBounds(String id, Rectangle bounds);
	void store();
	
	
}
