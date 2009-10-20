package ui.swing.presenter;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BinaryButtonBar extends JPanel {

	public BinaryButtonBar(final Runnable trueAction,
			final Runnable falseAction, final String trueLabel,
			final String falseLabel) {

		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.CENTER));

		okButton = new JButton(trueLabel);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				trueAction.run();
			}
		});

		cancelButton = new JButton(falseLabel);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				falseAction.run();
			}
		});

		add(okButton);
		add(cancelButton);
	}

	private static final long serialVersionUID = 1L;
	private final JButton okButton;
	private final JButton cancelButton;

	public void requestFocusOn(final String label) {
		if (okButton.getText().equals(label)) {
			okButton.requestFocus();
		}

		if (cancelButton.getText().equals(label)) {
			cancelButton.requestFocus();
		}

	}

}
