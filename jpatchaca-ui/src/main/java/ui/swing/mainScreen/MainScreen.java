package ui.swing.mainScreen;

import java.awt.Component;
import java.awt.Window;

public interface MainScreen {

	//TODO too generic methods, nothing to do with MainScreen, ExtendedStated, WTF?
	void setVisible(boolean b);

	void toFront();

	void setExtendedState(int state);

	void add(Component topBar, Object ext);

	void pack();

	Window getWindow();

	void dispose();

	void hide();

	Window owner();

}