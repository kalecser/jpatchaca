package newAndNoteworthy;

import org.reactive.Signal;

public interface NewAndNoteworthy {

	public Signal<Boolean> hasUnreadNewAndNoteworthy();

	public String getTextAndMarkAsRead();

}
