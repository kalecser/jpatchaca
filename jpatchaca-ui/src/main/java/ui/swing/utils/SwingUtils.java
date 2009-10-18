package ui.swing.utils;

import java.awt.Point;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.SwingUtilities;

import org.apache.commons.lang.UnhandledException;

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
		final FutureTask<T> future = new FutureTask<T>(callable);
		SwingUtilities.invokeLater(future);
		try {
			return future.get();
		} catch (final InterruptedException e) {
			throw new UnhandledException(e);
		} catch (final ExecutionException e) {
			throw new UnhandledException(e.getCause());
		}
	}

	public static void invokeAndWaitOrCry(final Runnable runnable) {

		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
			return;
		}

		try {
			SwingUtilities.invokeAndWait(runnable);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		} catch (final InvocationTargetException e) {
			throw new RuntimeException(e);
		}

	}

	public static void assertInEventDispatchThread() {
		if (!SwingUtilities.isEventDispatchThread()) {
			throw new IllegalStateException(
					"Must be called from event dispatch thread");
		}
	}

}
