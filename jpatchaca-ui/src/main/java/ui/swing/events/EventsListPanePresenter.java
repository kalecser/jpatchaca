package ui.swing.events;

import ui.swing.presenter.Presenter;

public class EventsListPanePresenter {

	private final Presenter presenter;
	private final EventsListPane pane;

	public EventsListPanePresenter(final Presenter presenter,
			final EventsListPane pane) {
		this.presenter = presenter;
		this.pane = pane;
	}

	public void show() {
		presenter.showOkCancelDialog(pane, "Events list - READ ONLY");
	}
}
