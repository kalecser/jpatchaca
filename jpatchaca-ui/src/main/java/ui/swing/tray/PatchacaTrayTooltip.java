package ui.swing.tray;

import lang.Maybe;

import org.reactive.Receiver;
import org.reactive.Signal;
import org.reactive.Source;

import tasks.tasks.taskName.TaskName;

public class PatchacaTrayTooltip {

	private String currentSelectedTask;
	private String currentActiveTask;
	private final Source<String> tooltip;

	public PatchacaTrayTooltip(final Signal<Maybe<TaskName>> activeTaskName,
			final Signal<Maybe<TaskName>> selectedTaskName) {

		tooltip = new Source<String>("");

		activeTaskName.addReceiver(new Receiver<Maybe<TaskName>>() {

			@Override
			public void receive(final Maybe<TaskName> taskName) {
				synchronized (PatchacaTrayTooltip.this) {
					currentActiveTask = (taskName == null ? "" : taskName
							.unbox().unbox());
					updateToolTip();
				}

			}
		});

		selectedTaskName.addReceiver(new Receiver<Maybe<TaskName>>() {

			@Override
			public void receive(final Maybe<TaskName> taskName) {
				synchronized (PatchacaTrayTooltip.this) {

					if (taskName != null) {
						currentSelectedTask = taskName.unbox().unbox();
					} else {
						currentSelectedTask = "";
					}

					updateToolTip();
				}
			}
		});
	}

	protected void updateToolTip() {

		if (currentActiveTask.equals("")) {
			tooltip.supply("Start task " + currentSelectedTask);
		} else {
			tooltip.supply("Patchaca timer - active: " + currentActiveTask);
		}

	}

	public Signal<String> output() {
		return tooltip;
	}

}
