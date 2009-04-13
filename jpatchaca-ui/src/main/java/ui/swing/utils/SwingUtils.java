package ui.swing.utils;

import java.awt.Point;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

import javax.swing.SwingUtilities;

import sun.swing.SwingUtilities2;

public class SwingUtils {

	public static void makeLocationrelativeToParent(final Window window,
			final Window owner) {
		if (owner == null) {
			return;
		}
		final Point location = derivePoint(owner.getLocation(), 50, 50);
		window.setLocation(location);
	}

	private static Point derivePoint(final Point basePoint, final int x,
			final int y) {
		return new Point((int) basePoint.getX() + x, (int) basePoint.getY() + y);
	}

	public static <T> T getOrCry(final Callable<T> callable) {
		try {
			return SwingUtilities2.submit(callable).get();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void invokeAndWaitOrCry(final Runnable runnable) {
		try {
			SwingUtilities.invokeAndWait(runnable);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		} catch (final InvocationTargetException e) {
			throw new RuntimeException(e);
		}

	}

}
