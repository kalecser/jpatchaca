package wheel.io.ui.impl;

import static wheel.i18n.Language.translate;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.security.InvalidParameterException;

import wheel.io.ui.TrayIcon;
import wheel.lang.exceptions.Catcher;
import wheel.lang.exceptions.PrintStackTracer;

public class TrayIconImpl implements TrayIcon {

	public static class SystemTrayNotSupported extends Exception {

		public SystemTrayNotSupported() {
			super(translate("Icon cannot be null"));
		}

		private static final long serialVersionUID = 1L;
	}

	private final java.awt.TrayIcon _trayIcon;

	private final Catcher _catcher;

	private Action _defaultAction;

	public TrayIconImpl(URL icon, Catcher catcherForThrowsDuringActionExecution)
			throws SystemTrayNotSupported {
		if (icon == null)
			throw new InvalidParameterException("Icon cannot be null");

		if (!SystemTray.isSupported())
			throw new SystemTrayNotSupported();

		final SystemTray tray = SystemTray.getSystemTray();
		final Image image = Toolkit.getDefaultToolkit().getImage(icon);
		final java.awt.TrayIcon trayIcon = createTrayIcon(image);
		// trayIcon.addMouseListener(mouseListener);

		try {
			tray.add(trayIcon);
		} catch (final AWTException e) {
			throw new SystemTrayNotSupported();
		}

		_trayIcon = trayIcon;
		_catcher = catcherForThrowsDuringActionExecution;
	}

	private java.awt.TrayIcon createTrayIcon(Image image) {
		final java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image, translate("Sneer"),
				new PopupMenu());
		trayIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1){
					if (_defaultAction != null)
						_defaultAction.run();
				}
			}
		});
		trayIcon.setImageAutoSize(false);
		return trayIcon;
	}

	public TrayIconImpl(URL userIcon) throws SystemTrayNotSupported {
		this(userIcon, new PrintStackTracer());
	}

	public void addAction(final Action action) {
		final PopupMenu popup = _trayIcon.getPopupMenu();
		if (popup.getItemCount() > 0)
			popup.addSeparator();

		final MenuItem menuItem = new MenuItem(action.caption());

		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ignored) {
				try {
					action.run();
				} catch (final Throwable t) {
					_catcher.catchThis(t);
				}
			}
		});

		popup.add(menuItem);
	}

	public void messageBalloon(String title, String message) {
		_trayIcon.displayMessage(title, message, MessageType.NONE);
	}
	
	public void clearActions(){
		_trayIcon.getPopupMenu().removeAll();
	}

	public void setDefaultAction(Action defaultAction) {
		_defaultAction = defaultAction;
	}

	public void dispose() {
		SystemTray.getSystemTray().remove(_trayIcon);		
	}
}
