/**
 * 
 */
package ui.swing.presenter;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

import ui.swing.utils.SwingUtils;

public class MockOkCancelPane implements ActionPane {

	protected AtomicBoolean okPressed = new AtomicBoolean(false);

	@Override
	public JPanel getPanel() {
		return new JPanel();
	}

	@Override
	public UIAction action() {
		return new UIAction() {
			@Override
			public void run() {
				okPressed.set(true);
			}
		};
	}

	public boolean okPressed() {
		return SwingUtils.getOrCry(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return okPressed.get();
			}
		});
	}

}