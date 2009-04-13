package ui.swing.presenter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import ui.swing.utils.SwingUtils;

public class OkCancelDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public OkCancelDialog(final ActionPane pane, String title, JFrame mainScreen) {
		super(mainScreen);
		SwingUtils.makeLocationrelativeToParent(this, mainScreen);
		this.setTitle(title);
		
		JPanel panel = pane.getPanel();
		if (panel == null)
			throw new IllegalStateException("panel must not be null");
		
		panel.setBorder(BorderFactory.createEmptyBorder());
		
		this.setLayout(new BorderLayout());
		this.add(pane.getPanel(), BorderLayout.CENTER);
		this.add(new OkCancelPanel(
				new Runnable() {
					@Override
					public void run() {
						doAction(pane);
					}
				},
				new Runnable() {
					@Override
					public void run() {
						OkCancelDialog.this.setVisible(false);
					}
				}
				), BorderLayout.SOUTH);
		
		
		this.getRootPane().registerKeyboardAction(new ActionListener(){@Override
			public void actionPerformed(ActionEvent e) {
				OkCancelDialog.this.setVisible(false);
			}}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		this.getRootPane().registerKeyboardAction(new ActionListener(){@Override
			public void actionPerformed(ActionEvent e) {
				doAction(pane);
			}}, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		this.pack();
	}

	private void doAction(final ActionPane pane) {
		new Thread(){
			@Override
			public void run() {
				internalDoAction(pane);
			}
		}.start();
	}

	private void internalDoAction(final ActionPane pane) {
		try {
			pane.action().run();
		} catch (ValidationException e) {
			JOptionPane.showMessageDialog(this, e.getUserMessage());
			return;
		}
		OkCancelDialog.this.setVisible(false);
	}

}
