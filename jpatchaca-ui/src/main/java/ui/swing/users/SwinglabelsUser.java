package ui.swing.users;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ui.swing.mainScreen.LabelsList;
import ui.swing.mainScreen.TaskContextMenu;
import basic.UserOperationCancelledException;

public class SwinglabelsUser implements LabelsUser {

	private final  TaskContextMenu taskContextMenu;
	private final LabelsList labelsList;
	
	public SwinglabelsUser(TaskContextMenu taskContextMenu, LabelsList labelsList){
		this.taskContextMenu = taskContextMenu;
		this.labelsList = labelsList;
		
	}
	
	@Override
	public String getNewLabelName(JFrame parent) throws UserOperationCancelledException {
		final String taskName = JOptionPane.showInputDialog(parent, "Enter label name");
		
		if (taskName == null) 
			throw new UserOperationCancelledException();
		
		return taskName;
		
	}

	@Override
	public String getLabelToAssignTaskTo() {
		
		final String dropToLabel = labelsList.getDropToLabel();
		if (dropToLabel != null)
			return dropToLabel;
		
		final String contextMenuLabelName = this.taskContextMenu.selectedLabelName();
		if (contextMenuLabelName != null)
			return contextMenuLabelName;
		
		throw new RuntimeException("There is no label to assign task to...");
	}

	@Override
	public String selectedLabel() {
		return labelsList.selectedLabel();
	}

}
