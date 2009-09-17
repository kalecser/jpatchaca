package ui.swing.mainScreen.periods;

import periods.Period;
import periodsInTasks.PeriodsInTasksSystem;
import ui.swing.tasks.SelectedTaskSource;

public class PeriodsListModel {

	private final PeriodsInTasksSystem periodsInTasksSystem;
	private final SelectedTaskSource selectedTask;

	public PeriodsListModel(final PeriodsInTasksSystem periodsInTasksSystem,
			final SelectedTaskSource selectedTask) {
		this.periodsInTasksSystem = periodsInTasksSystem;
		this.selectedTask = selectedTask;

	}

	public void removePeriod(final Period period) {
		periodsInTasksSystem.removePeriod(selectedTask.currentValue(), period);
	}

}
