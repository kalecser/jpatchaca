package ui.swing.mainScreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jdesktop.swingx.HorizontalLayout;

@SuppressWarnings("serial")
public class StatusBar extends JPanel {
	
	private JLabel messageLabel;
	private Timer timer;

	public StatusBar() {
		setPreferredSize(new Dimension(0, 18));
		setLayout(new HorizontalLayout(10));
		messageLabel = new JLabel();
		add(messageLabel);
		timer = new Timer(0, new ClearMessageAction());
		timer.setInitialDelay(10000);
		timer.setRepeats(false);
	}
	
	public void setInfoMessage(String message) {
		alterMessage(message, Color.BLACK);
		timer.start();
	}
	
	public void setErrorMessage(String message) {
		alterMessage(message, Color.RED);
		timer.start();
	}
	
	private void alterMessage(String message, Color color) {
		messageLabel.setText(message);
		messageLabel.setForeground(color);
		refresh();
	}

	public void clearMessage() {
		alterMessage("", Color.BLACK);
	}
	
	private void refresh() {
		setVisible(false);
		setVisible(true);
	}
	
	private class ClearMessageAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			clearMessage();
		}
		
	}
}
