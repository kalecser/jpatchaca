package ui.swing.presenter;

import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class Presenter {

	public Set<WeakReference<Window>> openedWindows = new LinkedHashSet<WeakReference<Window>>();
	private JFrame mainScreen;
	
	public void setMainScreen(JFrame mainScreen){
		this.mainScreen = mainScreen;
	}
	
	public JDialog showOkCancelDialog(final ActionPane pane, String title){
		
		final JDialog dialog = new OkCancelDialog(pane, title, mainScreen);
		return showDialog(dialog);
	}

	public JDialog showDialog(final JDialog dialog) {
		openedWindows.add(new WeakReference<Window>(dialog));
		dialog.setVisible(true);	
		
		return dialog;
	}

	public void closeAllWindows() {
		for (WeakReference<Window> windowRef : openedWindows){
			Window window = windowRef.get();
			if (window != null){
				window.setVisible(false);
				window.dispose();
			}
		}
		
	}
}
