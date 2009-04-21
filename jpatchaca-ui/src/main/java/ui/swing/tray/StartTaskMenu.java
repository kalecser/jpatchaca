package ui.swing.tray;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.reactive.Receiver;
import org.reactive.Signal;
import org.reactive.Source;

import tasks.tasks.TaskView;

public class StartTaskMenu {

	static final String START_TASK = "Start task";

	// bug, should depend on selectedtaskname only
	private final Signal<TaskView> selectedTaskSignal;
	private final PatchacaTrayModel model;
	private TaskView selectedTask;
	private final Source<String> selectedTaskName;

	public StartTaskMenu(final Signal<TaskView> selectedTaskSignal,
			final Source<String> selectedTaskName, final PatchacaTrayModel model) {
		this.selectedTaskSignal = selectedTaskSignal;
		this.selectedTaskName = selectedTaskName;
		this.model = model;
	}

	public MenuItem getMenu() {
		final MenuItem menuItem = new MenuItem("");

		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (selectedTask == null) {
					return;
				}

				model.startTaskIn(selectedTask, 0);
			}
		});

		selectedTaskSignal.addReceiver(new Receiver<TaskView>() {

			@Override
			public void receive(final TaskView pulse) {
				selectedTask = pulse;
				if (selectedTask == null) {
					menuItem.setLabel(START_TASK);
					menuItem.setEnabled(false);
					return;
				}
			}
		});

		selectedTaskName.addReceiver(new Receiver<String>() {

			@Override
			public void receive(final String pulse) {
				menuItem.setLabel(START_TASK + " " + pulse);
			}
		});

		return menuItem;
	}

}
