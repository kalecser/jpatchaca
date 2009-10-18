package ui.swing.presenter;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class YesNoPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final UIAction action;

	public YesNoPanel(final String caption, final UIAction action) {
		this.action = action;
		setLayout(new BorderLayout());

		add(new JLabel(caption), BorderLayout.CENTER);
		add(new BinaryButtonBar(yesAction(), noAction(), "yes", "no"),
				BorderLayout.SOUTH);

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

}
