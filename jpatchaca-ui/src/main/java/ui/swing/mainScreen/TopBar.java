package ui.swing.mainScreen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class TopBar extends JPanel {

	public interface Listener{
		void createTask();
		void startTask();
		void stopTask();
		void editTask();
		void removeTask();
		void options();
	}
	
	private JMenuItem createTaskItem;
	private JMenuItem startTaskItem;
	private JMenuItem stopTaskItem;
	private JMenuItem editTaskItem;
	private JMenuItem removeTaskItem;
	private JMenuItem exitItem;
	private JMenuItem optionsItem;
	
	private final List<Listener> listeners;

	public TopBar(){
		initialize();
		
		this.listeners = new ArrayList<Listener>();
		
		this.createTaskItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireCreateTask();
			}

		});
		
		this.startTaskItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireStartTask();
			}
		});
		
		this.stopTaskItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireStopTask();
			}
		});
		
		this.editTaskItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditTask();
			}
		});
		
		this.removeTaskItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireRemoveTask();
			}
		});
		
		this.optionsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireOptions();
			}
		});
		
		this.exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireExit();
			}
		});
	}

	protected void fireExit() {
		System.exit(0);
	}

	protected void fireRemoveTask() {
		for (final Listener listener : this.listeners){
			listener.removeTask();
		}	
	}

	protected void fireOptions() {
		for (final Listener listener : this.listeners){
			listener.options();
		}	
	}
	
	protected void fireStopTask() {
		for (final Listener listener : this.listeners){
			listener.stopTask();
		}	
	}

	protected void fireStartTask() {
		for (final Listener listener : this.listeners){
			listener.startTask();
		}			
	}

	protected void fireCreateTask() {
		for (final Listener listener : this.listeners){
			listener.createTask();
		}			
	}
	
	protected void fireEditTask() {
		for (final Listener listener : this.listeners){
			listener.editTask();
		}
	}

	private void initialize() {
		setLayout(new BorderLayout());
		
		final JMenuBar bar = new JMenuBar();
		bar.add(getTaskMenu());
		
		final JMenu viewMenu = new JMenu("View");
		bar.add(viewMenu);		
		
		add(bar, BorderLayout.NORTH);
	}

	private JMenu getTaskMenu() {
		final JMenu menu = new JMenu("File");
		this.createTaskItem = new JMenuItem("Create task");
		this.startTaskItem = new JMenuItem("Start task");
		this.stopTaskItem = new JMenuItem("Stop task");
		this.editTaskItem = new JMenuItem("Edit task");
		this.removeTaskItem = new JMenuItem("Remove task");
		this.optionsItem = new JMenuItem("Options");
		this.exitItem = new JMenuItem("Exit");
		
		menu.add(this.createTaskItem);
		menu.add(this.startTaskItem);
		menu.add(this.stopTaskItem);
		menu.add(this.editTaskItem);
		menu.add(this.removeTaskItem);
		menu.addSeparator();
		menu.add(this.optionsItem);
		menu.addSeparator();
		menu.add(this.exitItem);
		
		return menu;
	}
	
	public void addListener(Listener listener){
		this.listeners.add(listener);
	}


	public void stop() {}

	
	public void clickOnCreateTask() {
		createTaskItem.doClick();		
	}

	public void clickOnRemoveTask() {
		removeTaskItem.doClick();		
	}

	public void clickOnEditTask() {
		editTaskItem.doClick();		
	}

}
