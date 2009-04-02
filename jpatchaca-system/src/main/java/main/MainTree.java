package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTreeTable;
import org.picocontainer.MutablePicoContainer;

import periodsInTasks.PeriodsInTasksSystem;
import ui.swing.mainScreen.periods2.PeriodsTreeModel;
import ui.swing.mainScreen.periods2.PeriodsTreeRoot;

public class MainTree {

	
	public static void main(final String[] args) {
		final MutablePicoContainer container = Main.createDurableSWINGContainer();
		container.start();
		
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final PeriodsTreeModel periodsTreeModel = new PeriodsTreeModel(new PeriodsTreeRoot(container.getComponent(PeriodsInTasksSystem.class).getHome()));
		final JXTreeTable view = new JXTreeTable(periodsTreeModel);
		frame.setLayout(new BorderLayout());
		frame.add(new JScrollPane(view), BorderLayout.CENTER);
		final JButton comp = new JButton("Refresh");
		comp.addActionListener(new ActionListener( ) {
		
			@Override
			public void actionPerformed(final ActionEvent e) {
				periodsTreeModel.refresh();
			}
		});
		frame.add(comp, BorderLayout.NORTH);
		
		
		
		frame.pack();
		frame.setVisible(true);
	}
	
}
