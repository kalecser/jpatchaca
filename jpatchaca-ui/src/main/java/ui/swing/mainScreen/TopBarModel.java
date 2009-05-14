package ui.swing.mainScreen;

import ui.swing.events.EventsListPanePresenter;

public class TopBarModel {

	private final EventsListPanePresenter eventsListPresenter;

	public TopBarModel(final EventsListPanePresenter eventsListPresenter) {
		this.eventsListPresenter = eventsListPresenter;
	}

	public void openEventsList() {
		eventsListPresenter.show();
	}

}
