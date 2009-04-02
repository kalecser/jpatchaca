/**
 * 
 */
package ui.swing.mainScreen;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import tasks.tasks.TaskView;

class TaskListCellRenderer extends DefaultListCellRenderer{
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		final Component renderer = super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
		
		
		final TaskView task = (TaskView) value;
		if (task.isActive() && ! isSelected){
			renderer.setBackground(new Color(196,255,196));
		}

		if (task.budgetBallanceInHours() < 0) {
			renderer.setForeground(Color.RED);
		}
		
		if (task.budgetBallanceInHours() > 0) {
			renderer.setForeground(Color.BLUE);
		}
		
		return renderer;
	}		
}