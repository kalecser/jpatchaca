package wheel.lang.exceptions.mocks;

import wheel.lang.exceptions.Catcher;

public class CatcherMock implements Catcher {

	private Throwable _lastThrowable;

	@Override
	public void catchThis(Throwable t) {
		_lastThrowable = t;
	}

	public Throwable getLastThrowable() {
		return _lastThrowable;
	}

}
