/*
 * Created on 12/04/2009
 */
package ui.swing.mainScreen;

import static org.hamcrest.CoreMatchers.notNullValue;

import javax.swing.JFrame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.JFrameOperator;

import ui.swing.mainScreen.mock.TopBarModelMock;
import ui.swing.mainScreen.operators.TopBarMenuOperator;
import ui.swing.utils.UIEventsExecutor;

public class TopBarTest {

	private TopBarMenuOperator menuOperator;
	private ExpectExceptExec exexex;
	private TopBarModelMock modelMock;

	@Test
	public void testFireStartTask() {
		menuOperator.pushStartTaskmenu();
		Assert.assertThat(exexex.happened, notNullValue());
	}
	
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
		exexex = new ExpectExceptExec();
		
		modelMock = new TopBarModelMock();
		final TopBar bar = new TopBar(exexex, modelMock, null);
		bar.addListener(new Listener());
		final JFrame frame = new JFrame();
		frame.add(bar);
		frame.setVisible(true);
		final JFrameOperator frameOperator = new JFrameOperator(frame);
		menuOperator = new TopBarMenuOperator(
				frameOperator);
	}
	
	@SuppressWarnings("serial")
	class StartTaskException extends RuntimeException {
		// Empty test exception
	}
	class Listener extends TopBarListenerAdapter {

		@Override
		public void startTask() {
			throw new StartTaskException();
		}
	}
	
	class ExpectExceptExec implements UIEventsExecutor {

		StartTaskException happened;

		@Override
		public void execute(final Runnable command) {
			try {
				command.run();
			} catch (final StartTaskException e) {
				this.happened = e;
			}
		}
	}

}
