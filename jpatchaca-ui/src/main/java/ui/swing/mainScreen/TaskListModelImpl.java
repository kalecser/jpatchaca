/*
 * Created on 17/04/2009
 */
package ui.swing.mainScreen;

import ui.swing.tasks.StartTaskPresenter;

public final class TaskListModelImpl implements TaskListModel {

	private final StartTaskPresenter startTaskController;
	private final TooltipForTask tooltips;

	public TaskListModelImpl(final StartTaskPresenter startTaskController,
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
