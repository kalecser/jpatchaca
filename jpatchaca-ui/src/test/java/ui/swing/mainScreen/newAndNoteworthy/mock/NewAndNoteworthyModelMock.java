package ui.swing.mainScreen.newAndNoteworthy.mock;

import org.reactive.Signal;
import org.reactive.Source;

import ui.swing.mainScreen.newAndNoteworthy.NewAndNoteworthyModel;

public class NewAndNoteworthyModelMock implements NewAndNoteworthyModel{

	Source<Boolean> unreadNewAndNoteworthy = new Source<Boolean>(false);

	public void setUnreadNewandNoteworthy() {
		unreadNewAndNoteworthy.supply(true);
	}

	@Override
	public Signal<Boolean> hasUnreadNewAndNoteworthy() {
		return unreadNewAndNoteworthy;
	}

	@Override
	public void openNewAndNoteworthyScreen() {
		unreadNewAndNoteworthy.supply(false);
	}

}
