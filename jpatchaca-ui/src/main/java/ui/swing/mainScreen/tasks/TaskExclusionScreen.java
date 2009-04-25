package ui.swing.mainScreen.tasks;

import javax.swing.JOptionPane;

import ui.swing.mainScreen.SelectedTaskName;

public class TaskExclusionScreen {

	private final SelectedTaskName taskName;
	private final WindowManager manager;

	public TaskExclusionScreen(final SelectedTaskName taskName,
			final WindowManager manager) {
		this.taskName = taskName;
		this.manager = manager;

	}

	public int confirmExclusion() {
		return JOptionPane.showConfirmDialog(manager.getParentWindow(),
				"Do you really want to delete task "
						+ this.taskName.currentValue() + "?",
				"Task Removal Confirmation", JOptionPane.YES_NO_OPTION);
	}

}
