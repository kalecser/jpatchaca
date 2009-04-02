package ui.swing.tray.tests.environment;

import java.awt.Button;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import wheel.lang.Threads;

public class PathcacaTrayOperator {

	private static final long TIMEOUT = 5000;
	
	private PopupMenu menu = null;
	
	private PopupMenu getTrayIconMenu() {
		if (menu == null){
			final TrayIcon trayIcon = getTrayIcon();
			menu = trayIcon.getPopupMenu();
		}
		return menu;
	}

	private TrayIcon getTrayIcon() {
		return SystemTray.getSystemTray().getTrayIcons()[0];
	}
	
	public void startTaskHalfAnHourAgo(String taskName) {
		clickMenuByName("Start task.../"+taskName+"/30 minutes ago");
		
	}

	public void startNewTaskHalfAnHourAgo(String taskName) {
		clickMenuByName("Start task.../New task/30 minutes ago");
	}
	
	private void clickMenuByName(String name) {
		
		final String[] pathElements = name.split("/");
		
		MenuItem targetMenu = getTrayIconMenu();
		
		for (String element : Arrays.copyOf(pathElements, pathElements.length - 1)){
			targetMenu = menuItemByLabel(targetMenu, element);
		}
		
		
		for (final java.awt.event.ActionListener listener : targetMenu.getActionListeners()){
			new Thread(){ public void run() {
				listener.actionPerformed(new ActionEvent(new Button(), 0,pathElements[pathElements.length - 1]));
			};}.start();
		}
	}
	
	public void assertLastActiveTasks(List<String> expectedLastActiveTasksNames) {
	
		int index = 0;
		long timeout = 1500;
		
		for (String expectedTaskName : expectedLastActiveTasksNames){
			long start = System.currentTimeMillis();
			boolean found = false;
			String subMenuName = "";
			do {
				if (System.currentTimeMillis() - start > timeout)
					throw new IllegalStateException("Could not find task " + expectedTaskName + " at menu index " + index + " / found " + subMenuName + " instead");
				subMenuName = getSubMenuName("Start task.../", index);
				found = subMenuName.equals(expectedTaskName);
				Threads.sleepWithoutInterruptions(100);
			} while (!found);
			index++;
		}
		
	}

	public void assertActiveTask(String taskName) {

		String stopTaskMenuTemplate = "Stop task";
		if (taskName != null)
			stopTaskMenuTemplate+= " (" + taskName + ")";
		
		final long startTime = System.currentTimeMillis();
		while ((System.currentTimeMillis() - startTime) < TIMEOUT){
			 String stopTaskMenuLabel = getTrayIconMenu().getItem(1).getLabel();
			if (stopTaskMenuLabel.equals(stopTaskMenuTemplate))
				return;
		}
		
		if (taskName == null)
			return;
		
		final long startTime2 = System.currentTimeMillis();
		while ((System.currentTimeMillis() - startTime2) < TIMEOUT){
			 String tooltip = getTrayIcon().getToolTip();
			if (tooltip.contains(taskName))
				return;
		}
		
		throw new IllegalArgumentException("unable to find menuitem " + stopTaskMenuTemplate);
		
	}
	
	private MenuItem menuItemByLabel(MenuItem menu, String label) {
		
		long startTime = System.currentTimeMillis();
		while ((System.currentTimeMillis() - startTime) < TIMEOUT){
			PopupMenu popupMenu = ((PopupMenu)menu);
			for (int i = 0; i < popupMenu.getItemCount(); i++) {
				String menuLabel = popupMenu.getItem(i).getLabel();
				if (menuLabel.equals(label)){
					return popupMenu.getItem(i);
				}
			}
		}		
		
		throw new IllegalArgumentException("unable to find menuitem " + label);
	}
	
	private String getSubMenuName(String name, int subMenuIndex) {
		
		final String[] pathElements = name.split("/");
		
		final int length = pathElements.length;
		MenuItem targetMenu = getTrayIconMenu();
		
		for (String element : Arrays.copyOf(pathElements, length)){
			targetMenu = menuItemByLabel(targetMenu, element);
		}
		
		return ((PopupMenu)targetMenu).getItem(subMenuIndex).getLabel();
	}

	public void assertSelectedTask(String taskName) {
		
		throw new NotImplementedException();
	}


}
