package ui.swing.jira;

import java.awt.Component;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import jira.issue.JiraAction;

@SuppressWarnings("serial")
public class JiraActionCellEditor extends AbstractCellEditor implements TableCellEditor {

	private JComboBox actions;

	@Override
	public Object getCellEditorValue() {
		return actions.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		List<JiraAction> actionsArray = ((JiraIssueTableModel) table.getModel()).getActionsForRow(row);
		actionsArray.add(0, null);
		actions = new JComboBox(actionsArray.toArray());
		
		return actions;
	}

}
