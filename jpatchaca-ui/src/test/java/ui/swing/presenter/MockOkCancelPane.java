/**
 * 
 */
package ui.swing.presenter;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

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
		return okPressed.get();
	}

}