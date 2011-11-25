package ui.swing.mainScreen.newAndNoteworthy.mock;

import org.reactive.Signal;
import org.reactive.Source;

import newAndNoteworthy.NewAndNoteworthy;

public class NewAndNoteworthyMock implements NewAndNoteworthy {

	private String contents;

	@Override
	public Signal<Boolean> hasUnreadNewAndNoteworthy() {
		return new Source<Boolean>(false);
	}

	public void setContents(String string) {
		contents = string;
	}

	@Override
	public String getTextAndMarkAsRead() {
		return contents;
	}

}
