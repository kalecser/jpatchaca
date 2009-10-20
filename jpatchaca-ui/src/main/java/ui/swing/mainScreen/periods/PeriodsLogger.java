package ui.swing.mainScreen.periods;

import org.apache.log4j.Logger;

public class PeriodsLogger {

	private PeriodsLogger() {

	}

	public static Logger logger() {
		return Logger.getLogger(PeriodsLogger.class.getSimpleName());
	}
}
