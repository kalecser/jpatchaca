package ui.swing.mainScreen.newAndNoteworthy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.reactive.Receiver;

import ui.swing.mainScreen.TopBar;
import ui.swing.utils.UIEventsExecutor;

public class NewAndNoteworthyMenu {
	
	private final UIEventsExecutor executor;
	private final NewAndNoteworthyModel model;

	public NewAndNoteworthyMenu(final UIEventsExecutor executor, final NewAndNoteworthyModel model){
		this.executor = executor;
		this.model = model;
		
	}

	public JMenu getNewAndNoteworthyMenu() {
		final JMenu menu = new JMenu("New and noteworthy");
		JMenuItem item = menu.add("read");
		
		model.hasUnreadNewAndNoteworthy().addReceiver(new Receiver<Boolean>() {
			@Override
			public void receive(Boolean value) {
				if (value){
					menu.setIcon(new ImageIcon(TopBar.class.getResource("new.png")));
					menu.setToolTipText("new");
				} else {
					menu.setIcon(null);
					menu.setToolTipText("");
				}
					
			}
		});
		
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				executor.execute(new Runnable() {
					@Override
					public void run() {
						model.markNewAndNoteworthyAsRead();
					}
				});
			}
		});
		
		return menu;
	}

}
