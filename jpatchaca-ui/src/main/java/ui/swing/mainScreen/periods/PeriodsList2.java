package ui.swing.mainScreen.periods;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTreeTable;
import org.picocontainer.Startable;

import ui.swing.mainScreen.TaskList;

public class PeriodsList2 extends JFrame implements Startable{
	

	private static final long serialVersionUID = 1L;

	public PeriodsList2(TaskList taskList){

		this.setLayout(new FlowLayout());
		JXTreeTable periodsTreeTable = getPeriodsTreeTable(taskList);
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(periodsTreeTable), BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(200,200));
		this.pack();
		this.setVisible(true);
	}

	private JXTreeTable getPeriodsTreeTable(TaskList taskList) {
		
		JXTreeTable periodsTreeTable = new JXTreeTable(new PeriodsTreeTableModel(taskList));
		return periodsTreeTable;
	}

	@Override
	public void start() {
				
	}

	@Override
	public void stop() {
		
	}

}
