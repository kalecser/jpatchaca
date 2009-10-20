package ui.swing.presenter;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class YesNoPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final UIAction action;
	private final BinaryButtonBar buttonBar;

	public YesNoPanel(final String caption, final UIAction action) {
		this.action = action;
		setLayout(new BorderLayout());

		buttonBar = new BinaryButtonBar(yesAction(), noAction(), "yes", "no");
		add(new JLabel(caption), BorderLayout.CENTER);
		add(buttonBar, BorderLayout.SOUTH);

	}

	private Runnable noAction() {
		return new Runnable() {
			@Override
			public void run() {
				setVisible(false);
			}
		};
	}

	private Runnable yesAction() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					action.run();
				} catch (final ValidationException e) {
					throw new IllegalStateException(
							"Must not throw ValidationException");
				}
				setVisible(false);
			}
		};
	}

	public void requestFocusOnNo() {
		buttonBar.requestFocusOn("no");
	}

}
