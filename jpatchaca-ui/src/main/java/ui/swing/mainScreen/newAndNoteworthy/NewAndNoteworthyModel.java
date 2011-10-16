package ui.swing.mainScreen.newAndNoteworthy;

import org.reactive.Signal;

public interface NewAndNoteworthyModel {

	Signal<Boolean> hasUnreadNewAndNoteworthy();

	void markNewAndNoteworthyAsRead();

}
