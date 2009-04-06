package twitter;

import main.Main;

import org.junit.After;
import org.junit.Before;
import org.picocontainer.MutablePicoContainer;

import tasks.PatchacaTasksOperator;
import tasks.adapters.ui.PatchacaTasksOperatorUsingUI;
import basic.mock.MockHardwareClock;

public class TestTwitterUsingUI extends TwitterTests {

	private MutablePicoContainer container;
	private MockHardwareClock mockHardwareClock;

	@Before
	public void setup(){
		mockHardwareClock = new MockHardwareClock();
		container = Main.createSWINGContainerForTests(mockHardwareClock);		
		container.start();
	}
	
	@Override
	protected PatchacaTasksOperator getTasksOperator() {
		return new PatchacaTasksOperatorUsingUI(mockHardwareClock);
	}

	@Override
	protected TwitterOperator getTwitterOperator() {
		return new TwitterOperatorUsingUI();
	}
	
	@After
	public void tearDown(){
		container.stop();
	}

}
