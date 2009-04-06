package ui.swing.utils;

import java.awt.Point;
import java.awt.Window;

public class SwingUtils {

	
	public static void makeLocationrelativeToParent(Window window, Window owner) {
		if (owner == null)
			return;
		Point location = derivePoint(owner.getLocation(), 50, 50);
		window.setLocation(location);
	}

	private static Point derivePoint(Point basePoint, int x, int y) {
		return new Point((int)basePoint.getX() + x, (int)basePoint.getY() + y);
	}
	
}
