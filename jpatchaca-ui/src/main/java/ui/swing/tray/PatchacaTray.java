	package ui.swing.tray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SplashScreen;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.time.DateUtils;
import org.picocontainer.Startable;
import org.reactivebricks.pulses.Pulse;
import org.reactivebricks.pulses.Receiver;

import tasks.tasks.TaskView;
import ui.swing.utils.Whiteboard;
import wheel.io.ui.impl.TrayIconImpl.SystemTrayNotSupported;
import wheel.lang.Threads;
import basic.Alert;
import basic.AlertImpl;

public class PatchacaTray implements Startable {

	private static final String PATCHACA_TIMER = "Patchaca timer";
	private static final String TRAY_ICON_ACTIVE_PATH = "clock2.gif";
	private static final Image ACTIVE_ICON = iconImage(TRAY_ICON_ACTIVE_PATH);
	private static final String TRAY_ICON_INACTIVE_PATH = "clock.gif";
	private static final Image INACTIVE_ICON = iconImage(TRAY_ICON_INACTIVE_PATH);

	static final String START_TASK_SPECIAL = "Start task...";
	static final String STOP_TASK_SCPECIAL = "Stop task...";
	
	static final String OPEN = "open";
	static final String NEW_TASK = "New task";
	static final String EXIT = "Exit";
	static final String STOP_TASK = "Stop task";
	
	protected static final long HALF_AN_HOUR = DateUtils.MILLIS_PER_MINUTE * 30;
	protected static final long ONE_HOUR = DateUtils.MILLIS_PER_MINUTE * 60;

	private final AlertImpl stopTaskAlert;
	private final PatchacaTrayModel model;
	private final SpecialStartTaskSubMenu specialStartTaskMenu;
	
	private final Whiteboard whiteboard;
	
	private PopupMenu timerMenu;
	private PopupMenu lastTasksMenu;
	private TrayIcon trayIcon;
	
	protected AtomicLong lastClicktime = new AtomicLong();
	protected AtomicBoolean isprocessingClick = new AtomicBoolean(false);
		
	public PatchacaTray(PatchacaTrayModel model,  Whiteboard whiteboard) {
		this.model = model;
		
		this.whiteboard = whiteboard;

		specialStartTaskMenu = new SpecialStartTaskSubMenu(model);
		this.stopTaskAlert = new AlertImpl();

	}

	public void initialize() {

		TrayIcon icon = null;
		try {
			icon = createTrayIcon();
		} catch (SystemTrayNotSupported e) {
			showMainScreen();
			whiteboard
					.postMessage("System tray not supported.");
			return;
		}

		final PopupMenu timerMenu = createPopupMenu();

		bindToModel();
		bindStartTaskmenu();
		bindTrayicon();

		icon.setPopupMenu(timerMenu);

	}

	private void bindToModel() {
		this.model.setListener(new PatchacaTrayModelImpl.Listener() {
		
			@Override
			public void lastActiveTasksChanged() {
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run() {
						rebuildLastTasksMenu();
					}
				});
			}
		
		});
		
		bindTooltip();
	}

	private void bindTooltip() {
		
		model.tooltip().addReceiver(new Receiver<String>() {public void receive(Pulse<String> pulse) {
			trayIcon.setToolTip(pulse.value());
		}});
		
	}
	
	private void bindStartTaskmenu() {

		model.selectedTaskName().addReceiver(new Receiver<String>() {
			@Override
			public void receive(final Pulse<String> pulse) {
				updateStartStaskMenu(pulse.value());	
			}
		});
		
	}

	protected void bindTrayicon() {
		final MenuItem stopTaskItem = getMenuItemByText(STOP_TASK);
		final MenuItem stopTaskSpecialItem = getMenuItemByText(STOP_TASK_SCPECIAL);
		
		model.activeTaskName().addReceiver(new Receiver<String>() {
			@Override
			public void receive(Pulse<String> pulse) {
				if (pulse.value().equals("")) {
					trayIcon.setImage(INACTIVE_ICON);
					stopTaskItem.setLabel(STOP_TASK);
					stopTaskItem.setEnabled(false);
					stopTaskSpecialItem.setEnabled(false);
				} else {
					trayIcon.setImage(ACTIVE_ICON);
					stopTaskItem.setLabel(
							STOP_TASK + " (" + pulse.value() + ")");
					stopTaskItem.setEnabled(true);
					stopTaskSpecialItem.setEnabled(true);
				}		
			}
		});
		
		
	}

	private PopupMenu createPopupMenu() {
		this.timerMenu = new PopupMenu("Patchaca");
		this.timerMenu.add(new StartTaskMenu(model.selectedTaskSignal(), model.selectedTaskName(), model).getMenu());
		this.timerMenu.add(STOP_TASK);
		this.timerMenu.addSeparator();
		this.timerMenu.add(buildSpecialStartTaskMenu());
		this.timerMenu.add(buildSpecialStopTaskMenu());
		this.timerMenu.addSeparator();
		this.timerMenu.add(OPEN);
		this.timerMenu.add(EXIT);

		this.timerMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (event.getActionCommand().equals(OPEN)) {
					showMainScreen();
				} else if (event.getActionCommand().equals(EXIT)) {
					System.exit(0);
				} else if (event.getActionCommand().startsWith(STOP_TASK)) {
					PatchacaTray.this.stopTaskAlert.fire();
				}

			}
		});
		
		getMenuItemByText(STOP_TASK).setEnabled(false);
		getMenuItemByText(STOP_TASK_SCPECIAL).setEnabled(false);
		
		return this.timerMenu;
	}

	private MenuItem buildSpecialStopTaskMenu() {
		IntervalMenu specialStopTaskMenu = new IntervalMenu(PatchacaTray.STOP_TASK_SCPECIAL,
				new IntervalMenu.IntervalSelectedListener() {
					@Override
					public void intervalClicked(long millis) {
						model.stopTaskIn(millis);
				
					}
				});

		return specialStopTaskMenu;
	}

	private PopupMenu buildSpecialStartTaskMenu() {
		final PopupMenu lastTasksMenu = new PopupMenu(START_TASK_SPECIAL);		
		
		this.lastTasksMenu = lastTasksMenu;
		rebuildLastTasksMenu();
	
		return lastTasksMenu;
	}

	private TrayIcon createTrayIcon() throws SystemTrayNotSupported {

		if (!SystemTray.isSupported())
			throw new SystemTrayNotSupported();

		trayIcon = new TrayIcon(INACTIVE_ICON,
				PATCHACA_TIMER);
		trayIcon.setImageAutoSize(false);
		
		trayIcon.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e) {
			lastClicktime.set(System.currentTimeMillis());
			
			if (e.getButton() == MouseEvent.BUTTON2)
				model.createTaskStarted(0);
			
			if (e.getButton() != MouseEvent.BUTTON1)
				return;
			
			if (!isprocessingClick.getAndSet(true))
				new Thread(){public void run() {startStopTaskOnSingleClick(lastClicktime.get());}}.start();
				
			if (e.getClickCount() == 2)
				showMainScreen();

		}
		
		});

		try {
			tray().add(trayIcon);
		} catch (final AWTException e) {
			throw new RuntimeException(e);
		}

		return trayIcon;
	}

	private static Image iconImage(String resource) {
		final URL iconURL = PatchacaTray.class.getResource(
				resource);
		Image image = new ImageIcon(iconURL).getImage();
		return image;
	}

	private SystemTray tray() {
		return SystemTray.getSystemTray();
	}

	public void start() {
		initialize();
		final SplashScreen splashScreen = SplashScreen.getSplashScreen();
		if (splashScreen != null)
			splashScreen.close();
	}

	public void stop() {
		
		if (!SystemTray.isSupported())
			return;
		
		model.destroyMainScreen();
		tray().remove(trayIcon);
	}

	private void showMainScreen() {
		model.showMainScreen();
	}

	private MenuItem getMenuItemByText(String menuText) {
		
		for (int i = 0; i < timerMenu.getItemCount(); i++) {
			if (timerMenu.getItem(i).getLabel().startsWith(menuText))
				return timerMenu.getItem(i);
			
		}
		
		throw new IllegalArgumentException("menu not found");
	}

	public Alert stopTaskAlert() {
		return this.stopTaskAlert;
	}

	private void rebuildLastTasksMenu() {
		SwingUtilities.invokeLater(new Runnable() {	@Override public void run() {
		
			lastTasksMenu.removeAll();
				
			Collection<TaskView> lastStartedTasks = model.lastActiveTasks();
				
			int index = 0;
			for (TaskView task : lastStartedTasks){
				lastTasksMenu.add(specialStartTaskMenu.create(task));
				if (++index == 12)
					break;
			}
			
			lastTasksMenu.addSeparator();
			lastTasksMenu.add(createNewTaskMenu());
			
		}});
		
	}

	protected PopupMenu createNewTaskMenu() {
		IntervalMenu newTaskMenu = new IntervalMenu(NEW_TASK, 
				new IntervalMenu.IntervalSelectedListener() {
					@Override
					public void intervalClicked(long millis) {
						model.createTaskStarted(millis);	
					}
				}, true);
		
		return newTaskMenu;
	}

	private void startStopTaskOnSingleClick(long lastClickTime) {
		try{
			
			Threads.sleepWithoutInterruptions(300);
			boolean somebodyClickedWhileIWasSleeping = this.lastClicktime.get() != lastClickTime;
			if (somebodyClickedWhileIWasSleeping)
				return;
			
			if (model.hasActiveTask()){
				model.stopTaskIn(0);
				trayIcon.setImage(INACTIVE_ICON);
				return;
			}
			
			TaskView selectedTask = model.selectedTask();
			if (selectedTask != null){
				model.startTaskIn(selectedTask, 0);
				trayIcon.setImage(ACTIVE_ICON);
			}
			
		} finally {
			isprocessingClick.set(false);
		}
	}

	private void updateStartStaskMenu(final String selectedTaskName) {
		
		final MenuItem startTaskSpecial = getMenuItemByText(START_TASK_SPECIAL);
		
		SwingUtilities.invokeLater(new Runnable() {	public void run() {
			if (selectedTaskName.equals("")) {
				startTaskSpecial.setEnabled(false);
			} else {
				startTaskSpecial.setEnabled(true);
			}		
		}});
	}
}
