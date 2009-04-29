package ui.swing.tray;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import lang.Maybe;

import org.reactive.Receiver;
import org.reactive.Signal;

import tasks.tasks.TaskView;
import tasks.tasks.taskName.TaskName;

public class StartTaskMenu {

	static final String START_TASK = "Start task";

	// bug, should depend on selectedtaskname only
	private final Signal<TaskView> selectedTaskSignal;
	private final PatchacaTrayModel model;
	private TaskView selectedTask;
	private final Signal<Maybe<TaskName>> selectedTaskName;

	public StartTaskMenu(final Signal<TaskView> selectedTaskSignal,
			final Signal<Maybe<TaskName>> selectedTaskName,
			final PatchacaTrayModel model) {
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

				model.startTask(selectedTask, 0);
			}
		});

		selectedTaskSignal.addReceiver(new Receiver<TaskView>() {

			@Override
			public void receive(final TaskView pulse) {
				selectedTask = pulse;
				if (selectedTask == null) {
					menuItem.setLabel(START_TASK);
					return;
				}
			}
		});

		selectedTaskName.addReceiver(new Receiver<Maybe<TaskName>>() {

			@Override
			public void receive(final Maybe<TaskName> taskName) {
				if (taskName == null) {
					menuItem.setLabel(START_TASK);
				} else {
					menuItem.setLabel(START_TASK + " " + taskName);
				}
			}
		});

		return menuItem;
	}

}
