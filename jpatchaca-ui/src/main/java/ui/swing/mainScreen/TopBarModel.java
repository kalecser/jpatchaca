package ui.swing.mainScreen;

import org.reactive.Signal;

import ui.swing.mainScreen.newAndNoteworthy.NewAndNoteworthyModel;

public interface TopBarModel extends NewAndNoteworthyModel {

	public abstract void openEventsList();

	public abstract Signal<Boolean> hasUnreadNewAndNoteworthy();

	public abstract void markNewAndNoteworthyAsRead();

}