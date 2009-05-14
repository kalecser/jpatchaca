package ui.swing.presenter;

import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;

import ui.swing.utils.UIEventsExecutor;

public class Presenter {

	public Set<WeakReference<Window>> openedWindows = new LinkedHashSet<WeakReference<Window>>();
	private final UIEventsExecutor executor;
	private JFrame mainScreen;
	private JDialog dialog;

	public Presenter(final UIEventsExecutor executor) {
		this.executor = executor;
	}

	public void setMainScreen(final JFrame mainScreen) {
		this.mainScreen = mainScreen;
	}

	public JDialog showOkCancelDialog(final ActionPane pane, final String title) {

		dialog = new OkCancelDialog(executor, pane, title, mainScreen);
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

	}
}
