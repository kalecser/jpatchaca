package wheel.io.ui.impl.tests;

import java.awt.Rectangle;

import junit.framework.TestCase;
import wheel.io.files.impl.tranzient.TransientDirectory;
import wheel.io.ui.BoundsPersistence;
import wheel.io.ui.impl.DirectoryBoundsPersistence;

public class DirectoryBoundsPersistenceTest extends TestCase {

	
	public void testPersistBounds(){
		final TransientDirectory directory = new TransientDirectory();
		
		BoundsPersistence persistence = 
			new DirectoryBoundsPersistence(directory);
		
		
		final String id1 = "test";
		final String id2 = "test2";
		assertEquals(null, persistence.getStoredBounds(id1));
		assertEquals(null, persistence.getStoredBounds(id2));
		
		
		final Rectangle boundsId1 = new Rectangle(42,42,42,42);
		final Rectangle boundsId2 = new Rectangle(255,255,255,255);
		persistence.setBounds(id1, boundsId1);
		persistence.setBounds(id2, boundsId2);
		persistence.store();
		assertEquals(boundsId1, persistence.getStoredBounds(id1));
		assertEquals(boundsId2, persistence.getStoredBounds(id2));
		
		persistence = new DirectoryBoundsPersistence(directory);		
		assertEquals(boundsId1, persistence.getStoredBounds(id1));
		assertEquals(boundsId2, persistence.getStoredBounds(id2));
		
	}
}
