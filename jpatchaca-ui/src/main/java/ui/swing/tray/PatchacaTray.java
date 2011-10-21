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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import keyboardRotation.KeyboardRotationOptions;

import lang.Maybe;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.picocontainer.Startable;
import org.reactive.Receiver;

import tasks.TaskView;
import tasks.taskName.TaskName;
import ui.swing.presenter.Presenter;
import wheel.io.ui.impl.SystemTrayNotSupported;
import wheel.lang.Threads;
import basic.Alert;
import basic.AlertImpl;

public class PatchacaTray implements Startable {

	private static final String COPY_ACTIVE_TASK_NAME = "Copy active task name";
	private static final String PATCHACA_TIMER = "Patchaca timer";
	private static final String TRAY_ICON_ACTIVE_PATH = "jpon32.png";
	private static final Image ACTIVE_ICON = iconImage(TRAY_ICON_ACTIVE_PATH);
	private static final String TRAY_ICON_INACTIVE_PATH = "jpoff32.png";
	private static final Image INACTIVE_ICON = iconImage(TRAY_ICON_INACTIVE_PATH);
	private static final String TRAY_ICON_INACTIVE_ROTATION_ON_PATH = "jpoffTimer32.png";
	private static final Image INACTIVE_ROTATION_ON_ICON = iconImage(TRAY_ICON_INACTIVE_ROTATION_ON_PATH);
	private static final String TRAY_ICON_ACTIVE_ROTATION_ON_PATH = "jponTimer32.png";
	private static final String TRAY_ICON_ACTIVE_ROTATION_BLUE_TURN_ON_PATH = "jponTimerBlue32.png";
	private static final Image ACTIVE_ROTATION_ON_ICON = iconImage(TRAY_ICON_ACTIVE_ROTATION_ON_PATH);
	private static final Image ACTIVE_ROTATION_ON_ICON_BLUE = iconImage(TRAY_ICON_ACTIVE_ROTATION_BLUE_TURN_ON_PATH);

	static final String STOP_TASK_SCPECIAL = "Stop task...";

	static final String OPEN = "open";
	static final String NEW_TASK = "New task";
	static final String EXIT = "Exit";
	static final String STOP_TASK = "Stop task";

	private KeyboardRotationTimer timer;

	protected static final long HALF_AN_HOUR = DateUtils.MILLIS_PER_MINUTE * 30;
	protected static final long ONE_HOUR = DateUtils.MILLIS_PER_MINUTE * 60;

	final AlertImpl stopTaskAlert;
	final PatchacaTrayModel model;

	private PopupMenu timerMenu;
	TrayIcon trayIcon;
	
	protected AtomicLong lastClicktime = new AtomicLong();
	protected AtomicBoolean isprocessingClick = new AtomicBoolean(false);
	public boolean test_mode = false;
	private final Presenter presenter;
	private final KeyboardRotationOptions preferences;

	public PatchacaTray(final PatchacaTrayModel model, Presenter presenter, KeyboardRotationOptions preferences) {
		this.model = model;
		this.presenter = presenter;
		this.preferences = preferences;
		this.stopTaskAlert = new AlertImpl();
	}

	public void initialize() {
		final TrayIcon icon;
		try {
			icon = createTrayIcon();
		} catch (final SystemTrayNotSupported e) {
			showMainScreen();
			return;
		}
		
		showNotifications();
		bindToOrangeBlueTurn();

		this.timerMenu = createPopupMenu();
		findStopTaskMenuItem().setEnabled(false);
		findStopTaskSpecialMenuItem().setEnabled(false);

		bindToModel();
		bindTrayicon();

		icon.setPopupMenu(timerMenu);

	}

	private void showNotifications() {
		presenter.notification().addReceiver(new Receiver<String>() {
			@Override
			public void receive(String notification) {
				if (notification.equals(""))
						return;
				statusMessage(notification);
			}
		});
	}

	private void bindToOrangeBlueTurn() {
		presenter.isBlueTurn().addReceiver(new Receiver<Boolean>() {
			@Override
			public void receive(Boolean isBlueTurn) {
				if (isBlueTurn.booleanValue()){
					setBlueTurnIcon();
				} else {
					setOrangeTurnIcon();
				}
			}
		});
	}

	private void bindToModel() {
		bindTooltip();
	}

	private void bindTooltip() {

		model.tooltip().addReceiver(new Receiver<String>() {
			@Override
			public void receive(final String pulse) {
				trayIcon.setToolTip(pulse);
			}
		});

	}

	protected void bindTrayicon() {
		model.activeTaskName().addReceiver(new ActiveTaskNameReceiver());
	}

	private MenuItem findStopTaskSpecialMenuItem() {
		return getMenuItemByText(this.timerMenu, STOP_TASK_SCPECIAL);
	}

	private MenuItem findStopTaskMenuItem() {
		return getMenuItemByText(this.timerMenu, STOP_TASK);
	}

	void startTimer() {
		final String tempoDigitado = JOptionPane.showInputDialog(
				"Numero de Minutos para o Alerta?", "15");

		if (tempoDigitado != null) {
			int intTempoDigitado = 0;

			try {
				intTempoDigitado = new Integer(tempoDigitado).intValue();
			} catch (final NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Valor deve ser um inteiro!");
				this.startTimer();
			}
			
			if (model.hasActiveTask()) {
				setOrangeTurnIcon();
			} else {
				trayIcon.setImage(INACTIVE_ROTATION_ON_ICON);
			}
			
			
			timer = new KeyboardRotationTimer(intTempoDigitado, presenter, preferences);
			timer.start();

			KeyboardRotationTimer.setStatus(TimerStatus.ON);
			getMenuItemByText(this.timerMenu, "Turn On - Keyboard Rotation Alert").setEnabled(
					false);
			getMenuItemByText(this.timerMenu, "Turn Off - Keyboard Rotation Alert").setEnabled(
					true);
		}

	}

	void stopTimer() {
		KeyboardRotationTimer.setStatus(TimerStatus.OFF);

		timer.interrupt();
		
		if (model.hasActiveTask()) {
			trayIcon.setImage(ACTIVE_ICON);
		} else {
			trayIcon.setImage(INACTIVE_ICON);
		}
		
		statusMessage("Timer has been stopped!");
		KeyboardRotationTimer.setStatus(TimerStatus.OFF);
		getMenuItemByText(this.timerMenu, "Turn On - Keyboard Rotation Alert").setEnabled(true);
		getMenuItemByText(this.timerMenu, "Turn Off - Keyboard Rotation Alert").setEnabled(
				false);
		timer = null;
	}

	private void createTimerMenu(PopupMenu owner) {

		final MenuItem menuItemStart = new MenuItem(
				"Turn On - Keyboard Rotation Alert");
		final MenuItem menuItemStop = new MenuItem(
				"Turn Off - Keyboard Rotation Alert");

		final ActionListener actionStart = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				startTimer();
			}
		};

		final ActionListener actionStop = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				stopTimer();
			}
		};

		menuItemStart.addActionListener(actionStart);
		menuItemStop.addActionListener(actionStop);
		menuItemStop.setEnabled(false);

		owner.add(menuItemStart);
		owner.add(menuItemStop);
	}

	private PopupMenu createPopupMenu() {
		final PopupMenu menu = new PopupMenu("Patchaca");
		final MenuItem menuitemStartTask = new StartTaskMenu(model.selectedTaskSignal(), model
				.selectedTaskName(), model).getMenu();
		menu.add(menuitemStartTask);
		menu.add(STOP_TASK);
		menu.addSeparator();
		menu.add(COPY_ACTIVE_TASK_NAME);
		menu.addSeparator();
		final MenuItem menuItemSpecialStopTask = buildSpecialStopTaskMenu();
		menu.add(menuItemSpecialStopTask);
		menu.addSeparator();
		this.createTimerMenu(menu);
		menu.addSeparator();
		menu.add(OPEN);
		menu.add(EXIT);

		menu.addActionListener(new TimerMenuActionListener());

		return menu;
	}

	final class ActiveTaskNameReceiver implements
			Receiver<Maybe<TaskName>> {
		@Override
		public void receive(final Maybe<TaskName> taskName) {
			if (taskName == null) {
				onActiveTaskNameCleared();
			} else {
				onActiveTaskNameReceived(taskName.unbox());
			}
		}

	}

	void onActiveTaskNameCleared() {
		final MenuItem stopTaskItem = findStopTaskMenuItem();
		final MenuItem stopTaskSpecialItem = findStopTaskSpecialMenuItem();
		if (timer == null)
			trayIcon.setImage(INACTIVE_ICON);
		else
			trayIcon.setImage(INACTIVE_ROTATION_ON_ICON);	
		stopTaskItem.setLabel(STOP_TASK);
		stopTaskItem.setEnabled(false);
		stopTaskSpecialItem.setEnabled(false);
	}

	void onActiveTaskNameReceived(final TaskName taskName) {
		final MenuItem stopTaskItem = findStopTaskMenuItem();
		final MenuItem stopTaskSpecialItem = findStopTaskSpecialMenuItem();
		if (timer == null)
			trayIcon.setImage(ACTIVE_ICON);
		else
			setOrangeTurnIcon();	
		stopTaskItem.setLabel(STOP_TASK + " ("
				+ taskName.unbox() + ")");
		stopTaskItem.setEnabled(true);
		stopTaskSpecialItem.setEnabled(true);
	}

	final class TimerMenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent event) {
			String actionCommand = event.getActionCommand();
			if (actionCommand.equals(OPEN)) {
				showMainScreen();
			} else if (actionCommand.equals(EXIT)) {
				System.exit(0);
			} else if (actionCommand.startsWith(STOP_TASK)) {
				PatchacaTray.this.stopTaskAlert.fire();
			} else if (actionCommand.equals(COPY_ACTIVE_TASK_NAME)) {
				model.copyActiveTaskNameToClipboard();
			}

		}
	}
	
	private MenuItem buildSpecialStopTaskMenu() {
		final IntervalMenu specialStopTaskMenu = new IntervalMenu(
				PatchacaTray.STOP_TASK_SCPECIAL,
				new IntervalMenu.IntervalSelectedListener() {
					@Override
					public void intervalClicked(final long millis) {
						model.stopTaskIn(millis);

					}
				});

		return specialStopTaskMenu;
	}

	private TrayIcon createTrayIcon() throws SystemTrayNotSupported {

		if (!SystemTray.isSupported()) {
			throw new SystemTrayNotSupported();
		}

		trayIcon = new TrayIcon(INACTIVE_ICON, PATCHACA_TIMER);
		trayIcon.setImageAutoSize(false);

		trayIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				lastClicktime.set(System.currentTimeMillis());

				if (e.getButton() == MouseEvent.BUTTON2) {
					model.showStartTaskScreen();
				}

				if (e.getButton() != MouseEvent.BUTTON1) {
					return;
				}

				if (!isprocessingClick.getAndSet(true)) {
					new Thread() {
						@Override
						public void run() {
							startStopTaskOnSingleClick(lastClicktime.get());
						}
					}.start();
				}

				if (e.getClickCount() == 2) {
					showMainScreen();
				}

			}

		});

		addTrayIconOrCry();

		return trayIcon;
	}

	private static Image iconImage(String resource) {		
		final URL iconURL = PatchacaTray.class.getResource(resource);

		if (iconURL == null) {
			throw new IllegalStateException("Resource " + resource
					+ " for class " + PatchacaTray.class.getCanonicalName()
					+ " not found");
		}

		final ImageIcon imageIcon = new ImageIcon(iconURL);
		Image transparentImage = makeImageTransparent(imageIcon);
		Image scaledImage = scaleImage(transparentImage);
		return scaledImage;
	}

	private static Image makeImageTransparent(ImageIcon scaledImage) {
		if (SystemUtils.IS_OS_LINUX){
			return ImageUtils.replaceImageTrasparentByColor(scaledImage, ImageUtils.getTrayColor());			
		}
		
		return scaledImage.getImage();
	}

	private static Image scaleImage(final Image image) {
		
		if (SystemUtils.IS_OS_LINUX){
			Image scaledImage = image.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
			return scaledImage;			
		}
		
		if (SystemUtils.IS_OS_WINDOWS){
			Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			return scaledImage;			
		}
		
		return image;
	}

	private SystemTray tray() {
		return SystemTray.getSystemTray();
	}

	@Override
	public void start() {
		initialize();
		final SplashScreen splashScreen = SplashScreen.getSplashScreen();
		if (splashScreen != null) {
			splashScreen.close();
		}
	}

	@Override
	public void stop() {

		if (!SystemTray.isSupported()) {
			return;
		}

		model.destroyMainScreen();
		tray().remove(trayIcon);
	}

	void showMainScreen() {
		model.showMainScreen();
	}

	private static MenuItem getMenuItemByText(PopupMenu menu, final String text) {

		for (int i = 0; i < menu.getItemCount(); i++) {
			if (menu.getItem(i).getLabel().startsWith(text)) {
				return menu.getItem(i);
			}
		}

		throw new IllegalArgumentException("menu not found");
	}

	public Alert stopTaskAlert() {
		return this.stopTaskAlert;
	}

	protected PopupMenu createNewTaskMenu() {
		final IntervalMenu newTaskMenu = new IntervalMenu(NEW_TASK,
				new IntervalMenu.IntervalSelectedListener() {
					@Override
					public void intervalClicked(final long millis) {
						model.createTaskStarted(millis);
					}
				}, true);

		return newTaskMenu;
	}

	void startStopTaskOnSingleClick(final long lastClickTime) {
		try {

			Threads.sleepWithoutInterruptions(300);
			final boolean somebodyClickedWhileIWasSleeping = this.lastClicktime
					.get() != lastClickTime;
			if (somebodyClickedWhileIWasSleeping) {
				return;
			}

			if (model.hasActiveTask()) {
				model.stopTaskIn(0);
				return;
			}

			final TaskView selectedTask = model.selectedTask();
			if (selectedTask != null) {
				model.startTask(selectedTask, 0);
			}

		} finally {
			isprocessingClick.set(false);
		}
	}

	public void statusMessage(final String string) {
		if (test_mode) {
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				displayMessageInSwingThread(string);
			}

		});
	}

	void displayMessageInSwingThread(final String message) {
		trayIcon.displayMessage("Message", message,
				TrayIcon.MessageType.INFO);
	}
	
	
	public void ensureTrayIconIsVisibleDueToWindowsBug() {
		tray().remove(trayIcon);
		addTrayIconOrCry();
		
	}
	
	private void addTrayIconOrCry() {
		try {
			tray().add(trayIcon);
		} catch (final AWTException e) {
			throw new RuntimeException(e);
		}
	}

	void setOrangeTurnIcon() {
		trayIcon.setImage(ACTIVE_ROTATION_ON_ICON);
	}
	
	void setBlueTurnIcon() {
		trayIcon.setImage(ACTIVE_ROTATION_ON_ICON_BLUE);
	}
}
