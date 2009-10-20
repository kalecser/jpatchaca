package ui.swing.presenter;

import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lang.Maybe;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.utils.TimingUtils;

import org.apache.commons.lang.Validate;
import org.picocontainer.Startable;

import ui.swing.utils.UIEventsExecutor;

import commons.swing.FloatingArea;

public class Presenter implements Startable {

	public Set<WeakReference<Window>> openedWindows = new LinkedHashSet<WeakReference<Window>>();
	private final UIEventsExecutor executor;

	private Maybe<JFrame> mainScreen;
	private Maybe<FloatingArea> floatingArea;

	public Presenter(final UIEventsExecutor executor) {
		this.executor = executor;
	}

	public void setMainScreen(final JFrame mainScreen) {
		Validate.notNull(mainScreen);

		this.mainScreen = Maybe.wrap(mainScreen);
		this.floatingArea = Maybe.wrap(new FloatingArea(mainScreen));
	}

	public JDialog showOkCancelDialog(final ActionPane pane, final String title) {

		final OkCancelDialog dialog = new OkCancelDialog(executor, pane, title,
				((mainScreen == null) ? null : mainScreen.unbox()));
		return showDialog(dialog);
	}

	public JDialog showDialog(final JDialog dialog) {
		openedWindows.add(new WeakReference<Window>(dialog));
		dialog.setVisible(true);

		return dialog;
	}

	public void closeAllWindows() {
		for (final WeakReference<Window> windowRef : openedWindows) {
			final Window window = windowRef.get();
			if (window != null) {
				window.setVisible(false);
				window.dispose();
			}
		}

		for (final Window win : Window.getWindows()) {
			win.setVisible(false);
			win.dispose();
		}

	}

	public void showYesNoFloatingWindow(final String caption,
			final UIAction action) throws ValidationException {
		if (mainScreen == null) {
			throw new RuntimeException("Main screen is not set");
		}

		final int result = JOptionPane.showConfirmDialog(mainScreen.unbox(),
				caption, caption, JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			action.run();
		}
	}

	public void showMessageDialog(final String message) {

		if (floatingArea == null || mainScreen == null) {
			throw new RuntimeException("Main screen is not set");
		}

		final BalloonTip contents = new BalloonTip(mainScreen.unbox()
				.getRootPane(), message);
		final int tenSeconds = 10000;
		TimingUtils.showTimedBalloon(contents, tenSeconds);

		floatingArea.unbox().setContents(contents);
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
		closeAllWindows();
		floatingArea = null;
	}
}
