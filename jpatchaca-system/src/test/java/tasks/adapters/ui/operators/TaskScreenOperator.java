package tasks.adapters.ui.operators;

import java.awt.event.KeyEvent;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

import ui.swing.mainScreen.tasks.TaskScreenController;

public class TaskScreenOperator {

	
	private final JDialogOperator frame;
	private final JTextFieldOperator jiraKeyTextField;

	public TaskScreenOperator(){
		frame = new JDialogOperator(TaskScreenController.TITLE);
		jiraKeyTextField = new JTextFieldOperator(frame,0);
	}

	public void setTaskName(String taskNewName) {
		new JTextFieldOperator(frame,1).setText(taskNewName);		
	}

	public void clickOk() {
		new JButtonOperator(frame).clickMouse();
	}

	public void setTaskNameAndOk(String taskName) {
		setTaskName(taskName);
		clickOk();
		
	}

	public void setJiraKey(String jiraKey) {
		jiraKeyTextField.setText(jiraKey);
		jiraKeyTextField.pressKey(KeyEvent.VK_ENTER);
		
	}

	public void assertJiraKey(String jiraKey) {
		jiraKeyTextField.waitText(jiraKey);		
	}
	
}
