package ui.swing.presenter;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.JDialogOperator;

import ui.swing.presenter.mock.UIEventsExecutorMock;

public class PresenterTests {

	private PresenterImpl subject;

	@Test
	public void showShakingMessageWithTitleAndStopPresenter_DialogMustBeClosed(){
		
		String title = "test";
		String message = "shake shake shake";
		subject.showShakingMessageWithTitle(message, title);
		
		JDialogOperator dialog = new JDialogOperator();
		subject.stop();
		Assert.assertEquals(false, dialog.isVisible());
		
	}
	
	@Before
	public void before(){
		subject = new PresenterImpl(new UIEventsExecutorMock());
	}
	
	@After
	public void after(){
		subject.stop();
	}
	
}
