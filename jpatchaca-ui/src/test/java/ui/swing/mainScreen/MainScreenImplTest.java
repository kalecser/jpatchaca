/*
 * Created on 15/04/2009
 */
package ui.swing.mainScreen;

import org.junit.Ignore;
import org.junit.Test;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import org.reactive.Signal;
import org.reactive.Source;

import ui.swing.mainScreen.periods.PeriodsList;
import ui.swing.mainScreen.tasks.summary.SummaryScreen;
import ui.swing.presenter.PresenterImpl;
import wheel.io.ui.JFrameBoundsKeeper;
import wheel.io.ui.impl.JFrameBoundsKeeperImpl;

public class MainScreenImplTest {

	public static final class MockMainScreenModel implements MainScreenModel {

		@Override
		public void editSelectedTask() {
			// Do nothing
		}

		@Override
		public void removeSelectedTask() {
			// Do nothing
		}

		@Override
		public void showCreateTaskScreen() {
			// Do nothing
		}

		@Override
		public void showOptionsScreen() {
			// Do nothing
		}

		@Override
		public void showStartTaskScreen() {
			// Do nothing
		}

		@Override
		public void stopSelectedTask() {
			// Do nothing
		}

		@Override
		public Signal<String> titleSignal() {
			return new Source<String>("MOCK");
		}

	}

	@Test
	@Ignore("TaskList has a lot of dependencies and will need a model for itself")
	public void testStartTask() {
		final MutablePicoContainer container = new PicoBuilder()
				.withCaching()
					.build();
		container
				.addComponent(MainScreenModel.class, MockMainScreenModel.class);
		container.addComponent(MainScreen.class, MainScreenImpl.class);
		container.addComponent(TaskList.class);
		container.addComponent(PeriodsList.class);
		container.addComponent(SummaryScreen.class);
		container.addComponent(JFrameBoundsKeeper.class,
				JFrameBoundsKeeperImpl.class);
		container.addComponent(PresenterImpl.class);

		final MainScreenImpl impl = container
				.getComponent(MainScreenImpl.class);
		impl.setVisible(true);
		final JFrameOperator frameOperator = new JFrameOperator(impl);
		final JMenuBarOperator menuOperator = new JMenuBarOperator(
				frameOperator);
		menuOperator.pushMenu("File|Start task");
		System.out.println();
	}
}
