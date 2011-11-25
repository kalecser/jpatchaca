package ui.swing.presenter;

import java.awt.BorderLayout;
import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lang.Maybe;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.utils.TimingUtils;

import org.apache.commons.lang.Validate;
import org.picocontainer.Startable;
import org.reactive.Signal;
import org.reactive.Source;

import ui.swing.utils.SwingUtils;
import ui.swing.utils.UIEventsExecutor;

import commons.swing.FloatingArea;

public class PresenterImpl implements Startable, Presenter {

	public Set<WeakReference<Window>> openedWindows = new LinkedHashSet<WeakReference<Window>>();
	private final UIEventsExecutor executor;

	private Maybe<JFrame> mainScreen;
	private Maybe<FloatingArea> floatingArea;
	private Source<String> notification = new Source<String>("");
	private Source<Boolean> isBlueTurn = new Source<Boolean>(false);

	public PresenterImpl(final UIEventsExecutor executor) {
		this.executor = executor;
	}

	@Override
	public void setMainScreen(final JFrame mainScreen) {
		Validate.notNull(mainScreen);

		this.mainScreen = Maybe.wrap(mainScreen);
		this.floatingArea = Maybe.wrap(new FloatingArea(mainScreen));
	}

	@Override
	public JDialog showOkCancelDialog(final ActionPane pane, final String title) {

		final OkCancelDialog dialog = new OkCancelDialog(executor, pane, title,
				getOwner());
		return showDialog(dialog);
	}

	@Override
	public JDialog showDialog(final JDialog dialog) {
		openedWindows.add(new WeakReference<Window>(dialog));
		dialog.setVisible(true);

		return dialog;
	}
	
	@Override
	public void showShakingMessageWithTitle(String message, String title){
		ui.swing.utils.DialogShake.showDialogShaking(message, title);
	}

	@Override
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

	@Override
	public void showYesNoFloatingWindow(final String caption,
			final UIAction action) throws ValidationException {
		if (getMainScreen() == null) {
			throw new RuntimeException("Main screen is not set");
		}

		final int result = JOptionPane.showConfirmDialog(getMainScreen().unbox(),
				caption, caption, JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			action.run();
		}
	}
	
	@Override
	public void showMessageBalloon(final String message) {

		if (floatingArea == null || getMainScreen() == null ) {
			throw new RuntimeException("Main screen is not set");
		}

		final BalloonTip contents = new BalloonTip(getMainScreen().unbox()
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
		
		SwingUtils.invokeAndWaitOrCry(new Runnable() {	@Override public void run() {
			closeAllWindows();
			floatingArea = null;				
		}});
	}

	@Override
	public Maybe<JFrame> getMainScreen() {
		return mainScreen;
	}

	@Override
	public void showNotification(String message) {
		notification.supply(message);
	}

	@Override
	public Signal<String> notification() {
		return notification;
	}

	@Override
	public void showPlainDialog(JPanel panel, String title) {
		
		JDialog dialog = new JDialog(getOwner());
		dialog.setTitle(title);
		dialog.setLayout(new BorderLayout());
		dialog.add(panel);
		dialog.pack();
		showDialog(dialog);
	}
	
	private JFrame getOwner() {
		return (getMainScreen() == null) ? null : getMainScreen().unbox();
	}

	@Override
	public void setOrangeTurn() {
		isBlueTurn.supply(false);
	}

	@Override
	public void setBlueTurn() {
		isBlueTurn.supply(true);
	}
	
	@Override
	public Signal<Boolean> isBlueTurn(){
		return isBlueTurn;
	}

}
