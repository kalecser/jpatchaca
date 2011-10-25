package ui.swing.options;

import javax.swing.JDialog;

import ui.swing.presenter.Presenter;

public class OptionsScreenPresenter {

	private JDialog optionsScreen;
	final OptionsScreenModel optionsScreenModel;
	private final Presenter presenter;

	public OptionsScreenPresenter(final OptionsScreenModel optionsScreenModel,
			final Presenter presenter) {
		this.optionsScreenModel = optionsScreenModel;
		this.presenter = presenter;
	}

	public synchronized void show() {

		if (optionsScreen != null) {
			optionsScreen.setVisible(false);
			optionsScreen.dispose();
		}

		optionsScreen = presenter.showOkCancelDialog(
				new OptionsScreen(optionsScreenModel), "Options");

	}

}
