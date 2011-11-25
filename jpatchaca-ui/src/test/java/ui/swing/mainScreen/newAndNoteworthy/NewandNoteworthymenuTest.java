package ui.swing.mainScreen.newAndNoteworthy;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.JFrameOperator;

import ui.swing.mainScreen.newAndNoteworthy.mock.NewAndNoteworthyModelMock;
import ui.swing.mainScreen.newAndNoteworthy.operators.NewAndNoteworthyMenuOperator;
import ui.swing.presenter.mock.UIEventsExecutorMock;

public class NewandNoteworthymenuTest {

	private NewAndNoteworthyModelMock modelMock;
	private NewAndNoteworthyMenuOperator menuOperator;

	@Test
	public void IfUnreadNewAndNoteworthy_willShowAlertIconOnMenu(){
		setUnreadNewAndNoteworthy();
		menuOperator.waitAlerIconToShowOnNewandNoteworthy();
		menuOperator.readNewAndNoteworthy();
		menuOperator.waitAlerIconToVanishOnNewandNoteworthy();
	}
	
	private void setUnreadNewAndNoteworthy() {
		modelMock.setUnreadNewandNoteworthy();
	}
	
	@Before
	public void before(){
		
		modelMock = new NewAndNoteworthyModelMock();
		UIEventsExecutorMock executor = new UIEventsExecutorMock();
		NewAndNoteworthyMenu newAndNoteworthyMenu = new NewAndNoteworthyMenu(executor, modelMock);
		final JFrame frame = new JFrame();
		JMenuBar bar= new JMenuBar();
		bar.add(newAndNoteworthyMenu.getNewAndNoteworthyMenu());
		frame.add(bar);
		frame.setVisible(true);
		final JFrameOperator frameOperator = new JFrameOperator(frame);
		menuOperator = new NewAndNoteworthyMenuOperator(
				frameOperator);
	}

	
}
