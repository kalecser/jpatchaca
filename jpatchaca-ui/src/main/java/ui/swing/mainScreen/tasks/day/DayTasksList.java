package ui.swing.mainScreen.tasks.day;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import org.picocontainer.Startable;

import ui.swing.utils.SimpleInternalFrame;

public class DayTasksList extends SimpleInternalFrame implements Startable {

	private static final long serialVersionUID = 1L;
	private static final String panelTitle = "Day Tasks List";

	private final DayTasksTable dayTasksTable;
	private final WorklogTopPanel topPanel;

	public DayTasksList(DayTasksTable dayTasksTable, WorklogTopPanel topPanel) {
		super(DayTasksList.panelTitle);
		this.dayTasksTable = dayTasksTable;
		this.topPanel = topPanel;
		initialize();
	}

	private void initialize() {
		add(topPanel, BorderLayout.NORTH);
		add(new JScrollPane(dayTasksTable), BorderLayout.CENTER);
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}
}
