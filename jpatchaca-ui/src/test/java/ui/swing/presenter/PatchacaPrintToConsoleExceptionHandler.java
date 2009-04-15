/**
 * 
 */
package ui.swing.presenter;

import ui.swing.utils.PatchacaUncaughtExceptionHandler;

final class PatchacaPrintToConsoleExceptionHandler implements
		PatchacaUncaughtExceptionHandler {
	@Override
	public void uncaughtException(final Thread t, final Throwable e) {
		e.printStackTrace();

	}
}