package ui.swing.mainScreen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import ui.swing.utils.UIEventsExecutor;

@SuppressWarnings("serial")
public final class TopBar extends JPanel {

	public interface Listener {

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

	final Collection<Listener> listeners;
	private final UIEventsExecutor executor;

	public TopBar(final UIEventsExecutor executor) {
		this.executor = executor;

		initialize();

		this.listeners = new ArrayList<Listener>();

		this.createTaskItem.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				fireCreateTask();
			}

		});

		this.startTaskItem.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				fireStartTask();
			}
		});

		this.stopTaskItem.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				fireStopTask();
			}
		});

		this.editTaskItem.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				fireEditTask();
			}
		});

		this.removeTaskItem.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				fireRemoveTask();
			}
		});

		this.optionsItem.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				fireOptions();
			}
		});

		this.exitItem.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				fireExit();
			}
		});
	}

	protected void fireExit() {
		System.exit(0);
	}

	interface Event {

		void fire(Listener listener);
	}

	class NotifyListeners implements Runnable {

		private final Event event;

		NotifyListeners(final Event event) {
			this.event = event;
		}

		@Override
		public void run() {
			for (final Listener listener : TopBar.this.listeners) {
				this.event.fire(listener);
			}
		}

	}

	private void fireEvent(final Event event) {
		executor.execute(new NotifyListeners(event));
	}

	protected void fireRemoveTask() {
		fireEvent(new Event() {

			@Override
			public void fire(final Listener listener) {
				listener.removeTask();
			}

		});
	}

	protected void fireOptions() {
		fireEvent(new Event() {

			@Override
			public void fire(final Listener listener) {
				listener.options();
			}

		});
	}

	protected void fireStopTask() {
		fireEvent(new Event() {

			@Override
			public void fire(final Listener listener) {
				listener.stopTask();
			}

		});
	}

	protected void fireStartTask() {
		fireEvent(new Event() {

			@Override
			public void fire(final Listener listener) {
				listener.startTask();
			}

		});
	}

	protected void fireCreateTask() {
		fireEvent(new Event() {

			@Override
			public void fire(final Listener listener) {
				listener.createTask();
			}

		});
	}

	protected void fireEditTask() {
		fireEvent(new Event() {

			@Override
			public void fire(final Listener listener) {
				listener.editTask();
			}

		});
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

	public void addListener(final Listener listener) {
		this.listeners.add(listener);
	}

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
