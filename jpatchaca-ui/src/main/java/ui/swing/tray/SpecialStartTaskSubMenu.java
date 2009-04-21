package ui.swing.tray;

import java.awt.MenuItem;
import java.awt.PopupMenu;

import org.reactivebricks.pulses.Receiver;

import tasks.tasks.TaskView;

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
						model.startTaskIn(task, millis);
					}
				}, true);

		bindToTask(task, taskSpecialMenu);
		return taskSpecialMenu;
	}

	private void bindToTask(final TaskView task, final PopupMenu taskSpecialMenu) {
		task.nameSignal().addReceiver(new Receiver<String>() {

			@Override
			public void receive(final String pulse) {
				taskSpecialMenu.setLabel(pulse);
			}
		});
	}

}
