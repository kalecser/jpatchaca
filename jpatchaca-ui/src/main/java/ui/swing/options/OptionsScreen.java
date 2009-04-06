package ui.swing.options;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ui.swing.utils.SwingUtils;
import wheel.swing.CheckboxSignalBinder;
import wheel.swing.TextFieldBinder;

public class OptionsScreen {

	private JDialog optionsScreen;
	private final OptionsScreenModel optionsScreenModel;
	
	public OptionsScreen(final OptionsScreenModel optionsScreenModel){
		this.optionsScreenModel = optionsScreenModel;
	}

	private JButton createOkButton(final OptionsScreenModel optionsScreenModel,
			final JCheckBox twitterEnabled, final JTextField username,
			final JTextField password) {
		JButton okButton = new JButton("ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				optionsScreenModel.setConfig(twitterEnabled.isSelected(), username.getText(), password.getText());
				OptionsScreen.this.hide();
			}
		});
		return okButton;
	}
	
	protected synchronized void hide() {
		optionsScreen.setVisible(false);
		optionsScreen.dispose(); 
		optionsScreen = null;
		
	}

	@SuppressWarnings("serial")
	public synchronized void show(Window parent){
		
		if (optionsScreen != null){
			optionsScreen.setVisible(true);
			optionsScreen.toFront();
			return;
		}
			
		optionsScreen = new JDialog(parent, "Options");
		SwingUtils.makeLocationrelativeToParent(optionsScreen, parent);
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new MigLayout("wrap 4,fillx" ));
		
		final JCheckBox twitterEnabled = new JCheckBox("Twitter logging enabled");
		CheckboxSignalBinder.bind(twitterEnabled, optionsScreenModel.twitterEnabled());
		optionsPanel.add(twitterEnabled, "span 4");
		
		optionsPanel.add(new JLabel("Twitter username"));
		final JTextField username = new JTextField(30);
		TextFieldBinder.bind(username, optionsScreenModel.userName());
		optionsPanel.add(username, "growx,span 3");
		
		optionsPanel.add(new JLabel("Twitter Password"));
		final JTextField password = new JTextField(30);
		TextFieldBinder.bind(password, optionsScreenModel.password());
		optionsPanel.add(password, "growx,span 3");
		
		optionsPanel.add(new JComponent(){});
		optionsPanel.add(new JComponent(){});
		JButton okButton = createOkButton(optionsScreenModel, twitterEnabled,
				username, password);
		optionsPanel.add(okButton);
		JButton cancelButton = createCancelButton();
		optionsPanel.add(cancelButton);
		
		
		optionsPanel.setBorder(BorderFactory.createEmptyBorder());
		
		optionsScreen.add(optionsPanel);
		
		optionsScreen.pack();

		optionsScreen.setVisible(true);
	}
	
	private JButton createCancelButton() {
		
		JButton button = new JButton("cancel");
		button.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				OptionsScreen.this.hide();		
			}
		});
		return button;
	}

	public static void main(String[] args) {
		new OptionsScreen(new OptionsScreenMock()).show(new JFrame());
	}
	
}
