package ui.swing.tasks;

import ui.swing.presenter.Presenter;

public class StartTaskController {

	private final StartTaskScreen startTaskScreen;
	private final Presenter presenter;

	public StartTaskController(final Presenter presenter,
			final StartTaskScreen startTaskScreen) {
		this.presenter = presenter;
		this.startTaskScreen = startTaskScreen;
	}

	public void show() {
		presenter.showOkCancelDialog(startTaskScreen, "Start task");
	}
}
