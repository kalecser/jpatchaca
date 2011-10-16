package ui.swing.mainScreen.newAndNoteworthy;

import org.junit.Test;

import ui.swing.mainScreen.newAndNoteworthy.mock.NewAndNoteworthyMock;
import ui.swing.mainScreen.newAndNoteworthy.operators.NewAndNoteworthyScreenOperator;
import ui.swing.presenter.Presenter;
import ui.swing.presenter.PresenterImpl;
import ui.swing.presenter.mock.UIEventsExecutorMock;

public class NewAndNoteworthyModelTests {

	private NewAndNoteworthyMock newAndNoteworthy = new NewAndNoteworthyMock();

	@Test
	public void openNewAndNoteworthyScreenTest(){
		setNewAndNoteworthycontents("foobar");
		openNewAndNoteworthyScreen();
		
		new NewAndNoteworthyScreenOperator().waitVisible();
		new NewAndNoteworthyScreenOperator().waitText("foobar");
	}

	private void setNewAndNoteworthycontents(String string) {
		newAndNoteworthy.setContents(string);
	}

	private void openNewAndNoteworthyScreen() {
		UIEventsExecutorMock executor = new UIEventsExecutorMock();
		Presenter presenter = new PresenterImpl(executor);
		NewAndNoteworthyPresenter newAndNoteworthyPresenter = new NewAndNoteworthyPresenter(presenter, newAndNoteworthy);
		NewAndNoteworthyModelImpl model = new NewAndNoteworthyModelImpl(newAndNoteworthy, newAndNoteworthyPresenter);
		
		model.openNewAndNoteworthyScreen();
	}
	
}
