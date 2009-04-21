/*
 * Created on 17/04/2009
 */
package ui.swing.mainScreen;

import ui.swing.tasks.StartTaskController;

public final class TaskListModelImpl implements TaskListModel {

	private final StartTaskController startTaskController;
	private final TooltipForTask tooltips;

	public TaskListModelImpl(final StartTaskController startTaskController,
			final TooltipForTask tooltips) {
		this.startTaskController = startTaskController;
		this.tooltips = tooltips;
	}

	public void startTask() {
		startTaskController.show();
	}

	@Override
	public TooltipForTask getTooltips() {
		return this.tooltips;
	}

}
