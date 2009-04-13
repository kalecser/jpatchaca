package ui.swing.tray;

import javax.swing.SwingUtilities;

import org.picocontainer.Startable;

import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TaskView;
import ui.swing.mainScreen.Delegate;

public class TrayIconStartTaskMessage implements Startable {

	private final StartTaskDelegate startTask;
	private final PatchacaTray tray;

	public TrayIconStartTaskMessage(final PatchacaTray tray,
			final StartTaskDelegate startTask) {
		this.tray = tray;
		this.startTask = startTask;

	}

	@Override
	public void start() {
		startTask.addListener(new Delegate.Listener<TaskView>() {
			@Override
			public void execute(final TaskView object) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						tray
								.statusMessage("Task " + object.name()
										+ " started");
					}
				});
			}
		});
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
