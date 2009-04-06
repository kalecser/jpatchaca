package ui.swing.presenter;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import ui.swing.utils.SwingUtils;

public class Presenter {

	public Set<WeakReference<Window>> openedWindows = new LinkedHashSet<WeakReference<Window>>();
	private JFrame mainScreen;
	
	public void setMainScreen(JFrame mainScreen){
		this.mainScreen = mainScreen;
	}
	
	public JDialog showOkCancelDialog(final OkCancelPane pane, String title){
		
		final JDialog dialog = createOkCancelDialog(pane, title, mainScreen);
		openedWindows.add(new WeakReference<Window>(dialog));
		
		new Thread(){
			@Override
			public void run() {
				dialog.setVisible(true);
			}
		}.start();
		
		return dialog;
	}

	private static JDialog createOkCancelDialog(final OkCancelPane pane, String title, JFrame mainScreen) {
		final JDialog dialog = new JDialog(mainScreen);
		SwingUtils.makeLocationrelativeToParent(dialog, mainScreen);
		dialog.setTitle(title);
		
		JPanel panel = pane.getPanel();
		if (panel == null)
			throw new IllegalStateException("panel must not be null");
		
		panel.setBorder(BorderFactory.createEmptyBorder());
		
		dialog.setLayout(new BorderLayout());
		dialog.add(pane.getPanel(), BorderLayout.CENTER);
		dialog.add(new OkCancelPanel(
				new Runnable() {
					@Override
					public void run() {
						pane.okAction().run();
						dialog.setVisible(false);
					}
				},
				new Runnable() {
					@Override
					public void run() {
						dialog.setVisible(false);
					}
				}
				), BorderLayout.SOUTH);
		
		
		dialog.getRootPane().registerKeyboardAction(new ActionListener(){@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		dialog.pack();
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
