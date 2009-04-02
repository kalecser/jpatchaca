package tasks.adapters.ui;

import main.Main;

import org.picocontainer.MutablePicoContainer;

import periods.PatchacaPeriodsOperator;
import tasks.PatchacaTasksOperator;
import basic.mock.MockHardwareClock;


//bug move to ui
public class PeriodUsingUITest extends periods.PeriodsTest {

	private MockHardwareClock mockHardwareClock;
	private MutablePicoContainer container;
	private PatchacaTasksOperatorUsingUI patchacaTasksOperatorUsingUI;

	@Override
	protected void setUp() throws Exception {
		mockHardwareClock = new MockHardwareClock();
		container = Main.createSWINGContainerForTests(mockHardwareClock);		
		container.start();

		patchacaTasksOperatorUsingUI = new PatchacaTasksOperatorUsingUI(mockHardwareClock);
		
		super.setUp();
	}
	
	@Override
	protected PatchacaPeriodsOperator createPeriodsOperator() {
		
		return new PatchacaPeriodsOperatorUsingUI();
	}

	@Override
	protected PatchacaTasksOperator createTasksOperator() {
		return patchacaTasksOperatorUsingUI;
	}
	
	@Override
	protected void tearDown() throws Exception {
		container.stop();
	}

}
