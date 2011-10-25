package periods.adapters;

import java.awt.SystemTray;

import main.SWINGContainerForTestsBuilder;

import org.picocontainer.MutablePicoContainer;

import periods.PatchacaPeriodsOperator;
import tasks.PatchacaTasksOperator;
import tasks.adapters.ui.operators.PatchacaTasksOperatorUsingUI;
import basic.mock.MockHardwareClock;

//bug move to ui
public class PeriodUsingUITest extends periods.PeriodsTest {

	private MockHardwareClock mockHardwareClock;
	private MutablePicoContainer container;
	private PatchacaTasksOperatorUsingUI patchacaTasksOperatorUsingUI;

	@Override
	protected void setUp() throws Exception {
		mockHardwareClock = new MockHardwareClock();
		container = SWINGContainerForTestsBuilder.createSWINGContainerForTests(mockHardwareClock);
		container.start();

		if (!SystemTray.isSupported()) {
			return;
		}

		patchacaTasksOperatorUsingUI = new PatchacaTasksOperatorUsingUI(
				mockHardwareClock);

		super.setUp();
	}

	@Override
	protected PatchacaPeriodsOperator createPeriodsOperator() {
		if (!SystemTray.isSupported()) {
			return null;
		}

		return new PatchacaPeriodsOperatorUsingUI();
	}

	@Override
	protected PatchacaTasksOperator createTasksOperator() {
		if (!SystemTray.isSupported()) {
			return null;
		}

		return patchacaTasksOperatorUsingUI;
	}

	@Override
	protected void tearDown() throws Exception {
		container.stop();
	}

}
