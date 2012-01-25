package ui.swing.jira;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import jira.JiraIssueStatusManagement;
import jira.JiraOptions;
import jira.exception.JiraException;
import jira.issue.JiraIssue;
import jira.issue.RemoteJiraIssue;

import org.jdesktop.swingx.JXTable;
import org.picocontainer.Startable;

import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;
import ui.swing.presenter.ActionPane;
import ui.swing.presenter.Presenter;
import ui.swing.presenter.UIAction;
import ui.swing.presenter.ValidationException;
import basic.Delegate;

public class JiraIssueStatusActionPane implements ActionPane, UIAction,
		Startable {

	private final StartTaskDelegate startTaskDelegate;
	private final JiraIssueStatusManagement jiraIssueStatus;
	private final JiraOptions jiraOptions;
	private final Presenter presenter;
	private List<RemoteJiraIssue> issues;
	private JXTable table;

	public JiraIssueStatusActionPane(StartTaskDelegate startTaskDelegate,
			JiraIssueStatusManagement jiraIssueStatus, JiraOptions jiraOptions,
			Presenter presenter) {
		this.startTaskDelegate = startTaskDelegate;
		this.jiraIssueStatus = jiraIssueStatus;
		this.jiraOptions = jiraOptions;
		this.presenter = presenter;
	}

	@Override
	public UIAction action() {
		return this;
	}

	@Override
	public void run() throws ValidationException {
		JiraIssueTableModel model = (JiraIssueTableModel) table.getModel();

		for (int row = 0; row < model.getRowCount(); row++) {
			if (model.hasPendencies(row))
				try {
					model.processRow(row);
				} catch (JiraException e) {
					// TODO arrumar isso!!
					table.repaint();
					throw new ValidationException(e.getMessage());
				}
		}
	}

	@Override
	public JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(getScrollPane(), BorderLayout.CENTER);
		return panel;
	}

	private JScrollPane getScrollPane() {
		JiraIssueTableModel tableModel = new JiraIssueTableModel(issues,
				jiraIssueStatus);
		table = new JXTable(tableModel);

		int lineHeight = 20;
		int keyWidth = 60;
		int summaryWidth = 230;
		int actionsWidth = 100;
		int assignToMeWidth = 100;
		int scrollWidth = keyWidth + summaryWidth + actionsWidth + actionsWidth
				+ assignToMeWidth + summaryWidth + 10;

		table.setRowHeight(lineHeight);
		table.setIntercellSpacing(new Dimension(5, 1));

		TableColumnModel columnModel = table.getColumnModel();

		columnModel.getColumn(0).setPreferredWidth(keyWidth);
		columnModel.getColumn(0).setMaxWidth(keyWidth);
		columnModel.getColumn(1).setPreferredWidth(summaryWidth);
		columnModel.getColumn(2).setPreferredWidth(actionsWidth);
		columnModel.getColumn(2).setMaxWidth(actionsWidth);
		columnModel.getColumn(4).setPreferredWidth(actionsWidth);
		columnModel.getColumn(4).setMaxWidth(actionsWidth);

		TableColumn columnActions = columnModel
				.getColumn(JiraIssueTableModel.COLUMN_ACTIONS);
		columnActions.setCellEditor(new JiraActionCellEditor());
		columnActions.setPreferredWidth(actionsWidth);
		columnActions.setMaxWidth(actionsWidth);

		TableCellRenderer decorateCellRenderer = tableModel
				.decorateCellRenderer(table.getDefaultRenderer(String.class));
		columnActions.setCellRenderer(decorateCellRenderer);

		columnModel.getColumn(JiraIssueTableModel.COLUMN_COMMENT)
				.setPreferredWidth(summaryWidth);

		TableColumn columnAssign = columnModel
				.getColumn(JiraIssueTableModel.COLUMN_ASSIGN_TO);
		columnAssign.setPreferredWidth(assignToMeWidth);
		columnAssign.setMaxWidth(assignToMeWidth);
		columnAssign.setCellRenderer(decorateCellRenderer);

		JScrollPane scroll = new JScrollPane(table);
		Dimension scrollDimension = new Dimension(scrollWidth, lineHeight * 4);
		scroll.setPreferredSize(scrollDimension);
		return scroll;
	}

	@Override
	public void start() {
		startTaskDelegate.addListener(new Delegate.Listener<StartTaskData>() {

			@Override
			public void execute(StartTaskData startTaskData) {
				showIssuesForUpdateIfAny(startTaskData);
			}
		});
	}

	private void show(final List<RemoteJiraIssue> issuesForUpdate) {
		issues = issuesForUpdate;
		presenter.showOkCancelDialog(this, "Issue Actions");
	}

	@Override
	public void stop() {
		// Nothing to do.
	}

	void showIssuesForUpdateIfAny(StartTaskData startTaskData) {
		JiraIssue jiraIssue = startTaskData.taskData().getJiraIssue();

		if (jiraIssue == null)
			return;

		if (!jiraOptions.isJiraEnabled()
				|| !jiraOptions.isIssueStatusManagementEnabled())
			return;

		List<RemoteJiraIssue> issuesForUpdate = jiraIssueStatus
				.issuesForUpdate(jiraIssue);
		if (!issuesForUpdate.isEmpty())
			show(issuesForUpdate);
	}
}
