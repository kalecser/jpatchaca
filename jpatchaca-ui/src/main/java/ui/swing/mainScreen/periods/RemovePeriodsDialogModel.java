package ui.swing.mainScreen.periods;

import java.util.List;

import periods.Period;
import periodsInTasks.PeriodsInTasksSystem;
import tasks.TaskView;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.utils.SwingUtils;
import ui.swing.utils.UIEventsExecutor;

public class RemovePeriodsDialogModel {

	private final PeriodsInTasksSystem periodsInTasks;
	private final UIEventsExecutor executor;
	private final SelectedTaskSource selectedTask;

	public RemovePeriodsDialogModel(final PeriodsInTasksSystem periodsInTasks,
			final SelectedTaskSource selectedTask,
			final UIEventsExecutor executor) {
		this.selectedTask = selectedTask;
		this.periodsInTasks = periodsInTasks;
		this.executor = executor;
	}

	public void removePeriods(final List<Period> periods) {

		SwingUtils.assertInEventDispatchThread();

		final TaskView task = selectedTask.currentValue();

		executor.execute(new Runnable() {
			@Override
			public void run() {
				for (final Period period : periods) {
					periodsInTasks.removePeriod(task, period);
				}
			}
		});
	}
}
