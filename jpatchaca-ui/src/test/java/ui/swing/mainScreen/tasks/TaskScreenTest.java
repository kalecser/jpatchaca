package ui.swing.mainScreen.tasks;

import org.junit.After;
import org.junit.Test;

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
	public void testTaskScreen(){
		
		controller.createTask();
		
		
		
	}
	
	@After
	public void tearDown(){
		presenter.stop();
	}
	
}
