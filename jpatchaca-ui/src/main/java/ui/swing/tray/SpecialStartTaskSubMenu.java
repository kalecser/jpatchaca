package ui.swing.tray;

import java.awt.MenuItem;
import java.awt.PopupMenu;

import org.reactive.Receiver;

import tasks.TaskView;
import tasks.taskName.TaskName;

public class SpecialStartTaskSubMenu {

	private final PatchacaTrayModel model;

	public SpecialStartTaskSubMenu(final PatchacaTrayModel model) {
		this.model = model;
	}

	public synchronized MenuItem create(final TaskView task) {

		return createTaskMenu(task);
	}

	private PopupMenu createTaskMenu(final TaskView task) {
		final PopupMenu taskSpecialMenu = new IntervalMenu(task.name(),
				new IntervalMenu.IntervalSelectedListener() {
					@Override
					public void intervalClicked(final long millis) {
						model.startTask(task, millis);
					}
				}, true);

		bindToTask(task, taskSpecialMenu);
		return taskSpecialMenu;
	}

	private void bindToTask(final TaskView task, final PopupMenu taskSpecialMenu) {
		task.nameSignal().addReceiver(new Receiver<TaskName>() {

			@Override
			public void receive(final TaskName taskName) {
				taskSpecialMenu.setLabel(taskName.unbox());
			}
		});
	}

}
