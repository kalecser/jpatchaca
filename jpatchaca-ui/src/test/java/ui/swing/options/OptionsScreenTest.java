package ui.swing.options;



import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import ui.swing.presenter.PresenterImpl;
import ui.swing.presenter.mock.UIEventsExecutorMock;

public class OptionsScreenTest {
	
	private final Mockery m = new JUnit4Mockery();
	final OptionsScreenModel modelMock = m.mock(OptionsScreenModel.class);
	PresenterImpl presenter = new PresenterImpl(new UIEventsExecutorMock());
	
	@Ignore
	@Test
	public void testUseOldIcons(){
		openOptionsScreen();
		OptionsScreenOperator op = new OptionsScreenOperator();
	}

	private void openOptionsScreen() {
		new OptionsScreenPresenter(modelMock, presenter).show();
	}
	
	@After
	public void closeAllWindows(){
		presenter.closeAllWindows();
	}
	
}
