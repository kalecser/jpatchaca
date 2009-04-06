package ui.swing.presenter;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class OkCancelPanel extends JPanel {
	
	public OkCancelPanel(final Runnable okAction, final Runnable cancelAction) {
	
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JButton okButton = new JButton("ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okAction.run();
			}
		});

		JButton cancelButton = new JButton("cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelAction.run();
			}
		});
		
		add(okButton);
		add(cancelButton);
	}

	private static final long serialVersionUID = 1L;

	
	
}
