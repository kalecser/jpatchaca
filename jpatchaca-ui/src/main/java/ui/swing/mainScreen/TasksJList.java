/**
 * 
 */
package ui.swing.mainScreen;

import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListModel;


import tasks.tasks.TaskView;

class TasksJList extends JList {

	private static final long serialVersionUID = 1L;
	private final TooltipForTask tooltip;

	public TasksJList(final ListModel listModel, final TooltipForTask tooltip) {
		super(listModel);
		this.tooltip = tooltip;
	}

	@Override
	public String getToolTipText(final MouseEvent event) {
		final int index = locationToIndex(event.getPoint());
		final TaskView item = (TaskView) getModel().getElementAt(index);
		final String toolTip = tooltip.getTooltipForTask(item);
		return toolTip;
	}
}