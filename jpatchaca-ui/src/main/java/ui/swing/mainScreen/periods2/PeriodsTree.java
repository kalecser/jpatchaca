package ui.swing.mainScreen.periods2;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTreeTable;

public class PeriodsTree {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JXTreeTable view = new JXTreeTable(new PeriodsTreeModel(new MockPatchaca()));
		frame.add(new JScrollPane(view));
		
		
		
		frame.pack();
		frame.setVisible(true);
	}

}
