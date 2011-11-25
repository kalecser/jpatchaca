package ui.swing.mainScreen;

import ui.swing.events.EventsListPanePresenter;

public class TopBarModelImpl implements TopBarModel {

	private final EventsListPanePresenter eventsListPresenter;

	public TopBarModelImpl(final EventsListPanePresenter eventsListPresenter) {
		this.eventsListPresenter = eventsListPresenter;
	}

	@Override
	public void openEventsList() {
		eventsListPresenter.show();
	}

	

}
