package tasks.adapters.ui;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

import ui.swing.mainScreen.tasks.TaskScreenController;

public class TaskScreenOperator {

	
	private final JDialogOperator frame;

	public TaskScreenOperator(){
		frame = new JDialogOperator(TaskScreenController.TITLE);
	}

	public void setTaskName(String taskNewName) {
		new JTextFieldOperator(frame).setText(taskNewName);		
	}

	public void clickOk() {
		new JButtonOperator(frame).doClick();
	}

	public void setTaskNameAndOk(String taskName) {
		setTaskName(taskName);
		clickOk();
		
	}
	
}
