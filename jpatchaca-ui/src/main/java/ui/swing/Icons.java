package ui.swing;

import javax.swing.Icon;
import javax.swing.ImageIcon;


public class Icons {

	
	private static final String BASE_ICONS_DIR = "icons/";
	
	public static final Icon ADD_ICON = loadIcon("add.png");
	public static final Icon REMOVE_ICON = loadIcon("erase.png");
	
	private static ImageIcon loadIcon(String icon) {
		return new ImageIcon(Icons.class.getResource(BASE_ICONS_DIR + icon));
	}

}
