package ui.swing.mainScreen.tasks.worklog;

import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.StringValue;

public class WorklogTable extends JXTable {

    private static final long serialVersionUID = 1L;
    private final WorklogListModel dayTasksListModel;

    public WorklogTable(WorklogTableModel model, final WorklogListModel dayTasksListModel) {
        super(model);
        this.dayTasksListModel = dayTasksListModel;
        setSelectionListener();
        adjustColumnsAppearance();
        setSortable(false);
    }

    private void setSelectionListener() {
        getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                dayTasksListModel.setSelectedWorklogs(getSelectedRows());
            }
        });
    }

    private void adjustColumnsAppearance() {
        final TableColumnModel columnModel = getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(1).setMaxWidth(100);

        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(2).setMaxWidth(100);

        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(3).setMaxWidth(100);

        DefaultTableRenderer cellRenderer = new DefaultTableRenderer(StringValue.TO_STRING,
                SwingConstants.RIGHT);

        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(4).setMaxWidth(150);
        columnModel.getColumn(4).setCellRenderer(cellRenderer);

        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(5).setMaxWidth(150);
        columnModel.getColumn(5).setCellRenderer(cellRenderer);
    }

    void refrescate() {
        dayTasksListModel.refrescate();
    }
}
