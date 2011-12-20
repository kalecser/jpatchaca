package ui.swing.jira;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import jira.issue.JiraAction;
import jira.issue.RemoteJiraIssue;

@SuppressWarnings("serial")
public class JiraIssueTableModel extends AbstractTableModel {

	protected static final int COLUMN_ACTIONS = 3;
	protected static final int COLUMN_ASSIGN_TO_ME = 6;
	protected static final int COLUMN_COMMENT = 5;
	private final List<RemoteJiraIssue> issues;
	private Object[][] datas;
	private String[] columns;

	public JiraIssueTableModel(List<RemoteJiraIssue> issues) {
		this.issues = issues;
		defineColunms();
		defineDatas();
	}

	private void defineDatas() {
		datas = new Object[issues.size()][columns.length];
		for (int x = 0; x < issues.size(); x++) {
			RemoteJiraIssue issue = issues.get(x);
			datas[x][0] = issue.getKey();
			datas[x][1] = issue.getSummary();
			datas[x][2] = issue.getStatus();
			datas[x][4] = issue.getAssignee();
			datas[x][5] = "";
			datas[x][6] = Boolean.FALSE;
		}
	}

	private void defineColunms() {
		columns = new String[] { "Issue", "Summary", "Status", "Actions", "Assignee", "Comment", "Assign to me?" };
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return issues.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return datas[rowIndex][columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == COLUMN_ACTIONS || columnIndex == COLUMN_ASSIGN_TO_ME || columnIndex == COLUMN_COMMENT;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		datas[rowIndex][columnIndex] = aValue;
	}

	public List<JiraAction> getActionsForRow(int row) {
		return issues.get(row).getAvaiableActions();
	}

	public RemoteJiraIssue getIssueAt(int row) {
		return issues.get(row);
	}

	public JiraAction getActionAt(int row) {
		return (JiraAction) getValueAt(row, COLUMN_ACTIONS);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == COLUMN_ASSIGN_TO_ME)
			return Boolean.class;
		return super.getColumnClass(columnIndex);
	}

	public boolean isChangeAssignmentChecked(int row) {
		return (Boolean) getValueAt(row, COLUMN_ASSIGN_TO_ME);
	}

	public String getCommentAt(int row) {
		return (String) datas[row][COLUMN_COMMENT];
	}

}
