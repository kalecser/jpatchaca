package swing;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTable;

public class TTable {

	public static void main(final String[] args) {

		final JTable table = new MyTable();
		final JFrame frame = new JFrame();
		frame.add(table);
		
		
		frame.setPreferredSize(new Dimension(250,250));
		frame.pack();
		frame.setVisible(true);
	}

}
