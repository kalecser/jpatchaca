package ui.swing.mainScreen;

import junit.framework.Assert;

import org.junit.Test;
import org.netbeans.jemmy.operators.JPopupMenuOperator;

import ui.swing.mainScreen.mock.ModelMock;
import ui.swing.mainScreen.mock.TaskMock;

public class TaskContextMenuTest {

	ModelMock modelMock = new ModelMock();
	private TaskMock selectedTask = new TaskMock("foo");

	@Test
	public void openJiraIssueOnBrowser(){
		TaskContextMenu subject = showMenu();
		JPopupMenuOperator operator = new JPopupMenuOperator(subject);
		operator.pushMenu("open in browser");
		Assert.assertEquals("open task foo in browser", modelMock.getOperations());		
	}

	private TaskContextMenu showMenu() {
		TaskContextMenu subject = new TaskContextMenu();
		subject.setModel(modelMock);
		subject.show(null, 0, 0, selectedTask);
		return subject;
	}
	
}
