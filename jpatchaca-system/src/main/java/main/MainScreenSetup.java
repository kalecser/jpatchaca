/*
 * Created on 16/04/2009
 */
package main;

import org.picocontainer.Startable;

import ui.swing.mainScreen.MainScreenImpl;
import ui.swing.presenter.Presenter;

public class MainScreenSetup implements Startable {

	private final MainScreenImpl screen;
	private final Presenter presenter;

	public MainScreenSetup(final MainScreenImpl screen,
			final Presenter presenter) {
		this.screen = screen;
		this.presenter = presenter;
	}

	@Override
	public void start() {
		this.presenter.setMainScreen(this.screen);
	}

	@Override
	public void stop() {
		// Nothing special.
	}

}
