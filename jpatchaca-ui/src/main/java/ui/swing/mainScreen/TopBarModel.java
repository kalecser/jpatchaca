package ui.swing.mainScreen;

import org.reactive.Signal;

public interface TopBarModel {

	public abstract void openEventsList();

	public abstract Signal<Boolean> hasUnreadNewAndNoteworthy();

	public abstract void markNewAndNoteworthyAsRead();

}