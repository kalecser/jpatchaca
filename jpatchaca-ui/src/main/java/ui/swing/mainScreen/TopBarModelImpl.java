package ui.swing.mainScreen;

import org.reactive.Signal;
import org.reactive.Source;

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

	@Override
	public Signal<Boolean> hasUnreadNewAndNoteworthy() {
		return new Source<Boolean>(false);
	}

	@Override
	public void markNewAndNoteworthyAsRead() {
		throw new RuntimeException("Not implemented");
	}

}
