package ui.swing.presenter;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;

public class OkCancelDialogTest {

	private Presenter presenter;
	private MockOkCancelPane pane;

	@Before
	public void setup(){
		presenter = new Presenter();
		pane = new MockOkCancelPane();
		presenter.showOkCancelDialog(pane, "test dialog");
	}
	
	@Test
	public void testShowDialog(){
		new JDialogOperator("test dialog");
	}
	
	@Test
	public void testPressOk() throws InterruptedException{
		JDialogOperator dialog = new JDialogOperator("test dialog");
		new JButtonOperator(dialog, "ok").clickMouse();
		Assert.assertEquals(true, pane.okPressed());
		Assert.assertFalse(dialog.isVisible());
	}
	
	@Test
	public void testPressCancel() throws InterruptedException{
		JDialogOperator dialog = new JDialogOperator("test dialog");
		new JButtonOperator(dialog, "cancel").clickMouse();
		Assert.assertFalse(dialog.isVisible());
	}
	
	
	@After
	public void tearDown(){
		presenter.closeAllWindows();
	}
}
