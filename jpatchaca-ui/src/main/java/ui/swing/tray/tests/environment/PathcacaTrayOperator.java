package ui.swing.tray.tests.environment;

import java.awt.Button;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingUtilities;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.UnhandledException;

import wheel.lang.Threads;

public class PathcacaTrayOperator {

	private static final long TIMEOUT = 5000;

	private PopupMenu menu = null;

	private PopupMenu getTrayIconMenu() {
		if (menu == null) {
			final TrayIcon trayIcon = getTrayIcon();
			menu = trayIcon.getPopupMenu();
		}
		return menu;
	}

	private TrayIcon getTrayIcon() {
		return SystemTray.getSystemTray().getTrayIcons()[0];
	}

	private void clickMenuByName(final String name) {

		final String[] pathElements = name.split("/");

		MenuItem targetMenu = getTrayIconMenu();

		for (final String element : Arrays.copyOf(pathElements,
				pathElements.length - 1)) {
			targetMenu = menuItemByLabel(targetMenu, element);
		}

		for (final java.awt.event.ActionListener listener : targetMenu
				.getActionListeners()) {
			new Thread(new Runnable() {
				public void run() {
					listener.actionPerformed(new ActionEvent(new Button(), 0,
							pathElements[pathElements.length - 1]));
				}
			}).start();
		}
	}

	public void assertLastActiveTasks(
			final List<String> expectedLastActiveTasksNames) {

		int index = 0;
		final long timeout = 1500;

		for (final String expectedTaskName : expectedLastActiveTasksNames) {
			final long start = System.currentTimeMillis();
			boolean found = false;
			String subMenuName = "";
			do {
				if (System.currentTimeMillis() - start > timeout) {
					throw new IllegalStateException("Could not find task "
							+ expectedTaskName + " at menu index " + index
							+ " / found " + subMenuName + " instead");
				}
				subMenuName = getSubMenuName("Start task.../", index);
				found = subMenuName.equals(expectedTaskName);
				Threads.sleepWithoutInterruptions(100);
			} while (!found);
			index++;
		}

	}

	public void assertActiveTask(final String taskName) {

		String stopTaskMenuTemplate = "Stop task";
		if (taskName != null) {
			stopTaskMenuTemplate += " (" + taskName + ")";
		}

		final long startTime = System.currentTimeMillis();
		while ((System.currentTimeMillis() - startTime) < TIMEOUT) {
			final String stopTaskMenuLabel = getTrayIconMenu().getItem(1)
					.getLabel();
			if (stopTaskMenuLabel.equals(stopTaskMenuTemplate)) {
				return;
			}
		}

		throw new IllegalArgumentException("unable to find menuitem "
				+ stopTaskMenuTemplate);

	}

	static final class FindMenuItemByLabelInSwingThread implements
			Callable<MenuItem> {

		private final MenuItem m;
		private final String label;

		FindMenuItemByLabelInSwingThread(final MenuItem m, final String label) {
			this.m = m;
			this.label = label;
		}

		@Override
		public MenuItem call() throws Exception {
			final PopupMenu popupMenu = ((PopupMenu) m);
			for (int i = 0; i < popupMenu.getItemCount(); i++) {
				final MenuItem item = popupMenu.getItem(i);
				final String menuLabel = item.getLabel();
				if (menuLabel.equals(label)) {
					return item;
				}
			}
			return null;
		}

	}

	private MenuItem menuItemByLabel(final MenuItem m, final String label) {

		class FindMenuItemByLabelRepeatedly implements Callable<MenuItem> {

			@Override
			public MenuItem call() throws Exception {
				while (!Thread.currentThread().isInterrupted()) {
					final FutureTask<MenuItem> findOnce = new FutureTask<MenuItem>(
							new FindMenuItemByLabelInSwingThread(m, label));
					SwingUtilities.invokeLater(findOnce);
					final MenuItem menuItem = findOnce.get();
					if (menuItem != null) {
						return menuItem;
					}
				}
				throw new InterruptedException();
			}
		}

		final FutureTask<MenuItem> findRepeatedly = new FutureTask<MenuItem>(
				new FindMenuItemByLabelRepeatedly());
		final Thread t = new Thread(findRepeatedly);
		t.start();
		try {
			return findRepeatedly.get(TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (final TimeoutException e) {
			throw new IllegalArgumentException("unable to find menuitem "
					+ label, e);
		} catch (final InterruptedException e) {
			throw new UnhandledException(e);
		} catch (final ExecutionException e) {
			throw new UnhandledException(e);
		} finally {
			t.interrupt();
		}
	}

	private String getSubMenuName(final String name, final int subMenuIndex) {

		final String[] pathElements = name.split("/");

		final int length = pathElements.length;
		MenuItem targetMenu = getTrayIconMenu();

		for (final String element : Arrays.copyOf(pathElements, length)) {
			targetMenu = menuItemByLabel(targetMenu, element);
		}

		return ((PopupMenu) targetMenu).getItem(subMenuIndex).getLabel();
	}

	public void assertSelectedTask(final String taskName) {

		throw new NotImplementedException();
	}

	public void startNewTaskNow(final String taskName) {
		clickMenuByName("Start task.../New task/Now");
	}

}
