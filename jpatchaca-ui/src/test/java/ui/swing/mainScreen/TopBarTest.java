/*
 * Created on 12/04/2009
 */
package ui.swing.mainScreen;

import static org.hamcrest.CoreMatchers.notNullValue;

import javax.swing.JFrame;

import org.junit.Assert;
import org.junit.Test;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;

import ui.swing.utils.UIEventsExecutor;

public class TopBarTest {

	@Test
	public void testFireStartTask() {
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
		final ExpectExceptExec exexex = new ExpectExceptExec();
		final TopBar bar = new TopBar(exexex, null);
		bar.addListener(new Listener());
		final JFrame frame = new JFrame();
		frame.add(bar);
		frame.setVisible(true);
		final JFrameOperator frameOperator = new JFrameOperator(frame);
		final JMenuBarOperator menuOperator = new JMenuBarOperator(
				frameOperator);
		menuOperator.pushMenu("File|Start task");
		Assert.assertThat(exexex.happened, notNullValue());
	}
}
