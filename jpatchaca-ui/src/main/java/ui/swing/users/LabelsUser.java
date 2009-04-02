package ui.swing.users;

import javax.swing.JFrame;

import basic.UserOperationCancelledException;

public interface LabelsUser {

	String getNewLabelName(JFrame parent) throws UserOperationCancelledException;
	String getLabelToAssignTaskTo();
	String selectedLabel();
	

}
