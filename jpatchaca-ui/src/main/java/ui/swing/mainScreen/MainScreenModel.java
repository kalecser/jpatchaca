/*
 * Created on 15/04/2009
 */
package ui.swing.mainScreen;

import org.reactive.Signal;

public interface MainScreenModel {

	Signal<String> titleSignal();

	void removeSelectedTask();

	void stopSelectedTask();

	void editSelectedTask();

	void showCreateTaskScreen();

	void showStartTaskScreen();

	void showOptionsScreen();

}
