package ui.swing.mainScreen.tasks;

import java.awt.Window;

public class WindowManager {

	private Window mainWindow;

	public void setMainWindow(Window window){
		this.mainWindow = window;		
	}
	
	public Window getParentWindow() {
		if (mainWindow == null)
			throw new IllegalStateException("Tehre is no main window");
		
		return mainWindow;
	}

}
