package swing.test;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;

import swing.JXTableImproved;


//edit cells like excel
public class JXTableImprovedTestFrame {

	private JFrame frame;
	
	public JXTableImprovedTestFrame(String[] columnNames, String[][] values) {
		frame = new JFrame();
		
		final JXTable table = new JXTableImproved(values, columnNames);
		
		frame.add(new JScrollPane(table));
		frame.pack();
		
		frame.setVisible(true);
	}
	
	

	public void dispose() {
		frame.dispose();
	}

}
