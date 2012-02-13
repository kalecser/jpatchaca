package ui.swing.mainScreen.tasks.worklog;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import org.picocontainer.Startable;

import ui.swing.utils.SimpleInternalFrame;

public class WorklogList extends SimpleInternalFrame implements Startable {

    private static final long serialVersionUID = 1L;
    private static final String panelTitle = "Day Tasks List";

    private final WorklogTable dayTasksTable;
    private final WorklogTopPanel topPanel;

    public WorklogList(WorklogTable dayTasksTable, WorklogTopPanel topPanel) {
        super(WorklogList.panelTitle);
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

    public void refrescate() {
        dayTasksTable.refrescate();
        topPanel.refrescate();
    }
}
