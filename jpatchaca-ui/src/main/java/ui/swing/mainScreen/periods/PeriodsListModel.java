package ui.swing.mainScreen.periods;

import periods.Period;
import periodsInTasks.PeriodsInTasksSystem;
import tasks.TaskView;
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
	
	public int selectedPeriodIndex(Period selectedPeriod) {
		final TaskView currentSelectedTask = selectedTask.currentValue();
		return currentSelectedTask.periods().indexOf(selectedPeriod);
	}


}
