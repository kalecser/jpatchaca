package ui.swing.presenter.mock;

import ui.swing.utils.UIEventsExecutor;

public class UIEventsExecutorMock implements UIEventsExecutor {

	@Override
	public void execute(Runnable arg0) {
		throw new RuntimeException("Not implemented");
	}

}
