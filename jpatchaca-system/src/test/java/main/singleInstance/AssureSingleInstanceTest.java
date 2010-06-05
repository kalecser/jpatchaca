package main.singleInstance;

import org.junit.Test;

public class AssureSingleInstanceTest {

	@Test
	public void testTwoInstances() {
		try {
			AssureSingleInstance.registerAsRunning();
		} catch (final AlreadyRunningApplicationException e) {
			throw new RuntimeException(
					"AlreadyRunningApplicationException was thrown when opening one instance",
					e);
		}
		try {
			AssureSingleInstance.registerAsRunning();
		} catch (final AlreadyRunningApplicationException e) {
			return;
		}
		throw new RuntimeException(
				"AlreadyRunningApplicationException was not thrown when opening another instance");
	}
}
