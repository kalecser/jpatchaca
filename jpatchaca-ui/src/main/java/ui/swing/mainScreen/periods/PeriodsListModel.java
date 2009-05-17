package ui.swing.mainScreen.periods;

import periodsInTasks.PeriodsInTasksSystem;
import tasks.TaskView;

public class PeriodsListModel {

	private final PeriodsInTasksSystem periodsInTasksSystem;

	public PeriodsListModel(final PeriodsInTasksSystem periodsInTasksSystem){
		this.periodsInTasksSystem = periodsInTasksSystem;
		
	}
	
	public void removePeriod(final TaskView selectedTask, final int selectedPeriodIndex) {
		periodsInTasksSystem.removePeriod(selectedTask, selectedTask.getPeriod(selectedPeriodIndex));
	}

}
