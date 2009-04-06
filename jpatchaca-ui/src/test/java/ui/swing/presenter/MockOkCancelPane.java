/**
 * 
 */
package ui.swing.presenter;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

public class MockOkCancelPane implements OkCancelPane {

	protected AtomicBoolean okPressed = new AtomicBoolean(false);

	@Override
	public JPanel getPanel() {
		return new JPanel();
	}

	@Override
	public Runnable okAction() {
		return new Runnable() {
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