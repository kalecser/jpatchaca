package ui.swing.mainScreen.mock;

import org.reactive.Signal;
import org.reactive.Source;

import ui.swing.mainScreen.TopBarModel;

public class TopBarModelMock implements TopBarModel {

	Source<Boolean> unreadNewAndNoteworthy = new Source<Boolean>(false);
	
	@Override
	public void openEventsList() {
		throw new RuntimeException("Not implemented");
	}

	public void setUnreadNewandNoteworthy() {
		unreadNewAndNoteworthy.supply(true);
	}

	@Override
	public Signal<Boolean> hasUnreadNewAndNoteworthy() {
		return unreadNewAndNoteworthy;
	}

	@Override
	public void markNewAndNoteworthyAsRead() {
		unreadNewAndNoteworthy.supply(false);
	}

}
