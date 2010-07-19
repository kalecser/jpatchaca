package ui.swing.mainScreen.periods;

import java.util.List;

import periods.Period;
import ui.swing.presenter.Presenter;
import ui.swing.presenter.UIAction;
import ui.swing.presenter.ValidationException;

public class RemovePeriodsDialogController {

	private final Presenter presenter;
	private final RemovePeriodsDialogModel model;

	public RemovePeriodsDialogController(final Presenter presenter,
			final RemovePeriodsDialogModel model) {
		this.presenter = presenter;
		this.model = model;
	}

	public void confirmPeriodsRemoval(final List<Period> periods) {
		final String question = "Remove selected "
				+ ((periods.size() > 1) ? "periods" : "period") + "?";

		try {
			presenter.showYesNoFloatingWindow(question, new UIAction() {
				@Override
				public void run() throws ValidationException {
					model.removePeriods(periods);
				}
			});
		} catch (final ValidationException e) {
			presenter.showMessageBalloon("Error removing periods");
			PeriodsLogger.logger()
					.error("Error removing periods " + periods, e);
		}
	}
}
