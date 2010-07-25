package ui.swing.mainScreen.tasks;

import org.junit.After;
import org.junit.Test;

import tasks.adapters.ui.operators.TaskScreenOperator;
import ui.swing.mainScreen.tasks.mock.MockJira;
import ui.swing.mainScreen.tasks.mock.MockTaskScreenModel;
import ui.swing.presenter.Presenter;
import ui.swing.utils.UIEventsExecutorImpl;

import basic.FormatterImpl;

public class TaskScreenTest {

	
	MockJira mockJira = new MockJira();
	MockTaskScreenModel mockModel = new MockTaskScreenModel();
	Presenter presenter = new Presenter(new UIEventsExecutorImpl(null));
	TaskScreenController controller = new TaskScreenController(new FormatterImpl(), mockModel, presenter , mockJira);
	
	@Test
	public void testTaskNameAutoCompleteFromJira(){
		
		controller.createTask();
		
		TaskScreenOperator operator = new TaskScreenOperator();
		operator.setJiraKey("test");
		operator.assertName("[jira-issue-key] jira-issue-summary");
		
	}
	
	@Test
	public void testTaskNameAutoCompleteFromJiraOnlyIfNameFieldIsEmpty(){
		
		controller.createTask();
		
		TaskScreenOperator operator = new TaskScreenOperator();
		operator.setTaskName("foobar");
		operator.setJiraKey("test");
		operator.assertName("foobar");
		
	}
	
	
	@After
	public void tearDown(){
		presenter.stop();
	}
	
}
