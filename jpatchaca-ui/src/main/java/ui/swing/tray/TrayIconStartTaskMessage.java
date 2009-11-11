package ui.swing.tray;

import javax.swing.SwingUtilities;

import org.apache.commons.lang.time.DateUtils;
import org.picocontainer.Startable;

import basic.Delegate;

import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;

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
		startTask.addListener(new Delegate.Listener<StartTaskData>() {
			@Override
			public void execute(final StartTaskData object) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						final Integer millisecondsAgo = object
								.millisecondsAgo();
						tray
								.statusMessage("Task "
										+ object.taskName()
										+ " started "
										+ ((millisecondsAgo == 0) ? "now "
												: (millisecondsAgo
														/ DateUtils.MILLIS_PER_MINUTE + " minutes ago")));
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
