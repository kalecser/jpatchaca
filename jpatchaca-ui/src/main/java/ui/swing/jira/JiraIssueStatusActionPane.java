package ui.swing.jira;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jira.JiraAction;
import jira.JiraIssue;
import jira.JiraOptions;
import jira.RemoteJiraIssue;

import org.jdesktop.swingx.JXTable;
import org.picocontainer.Startable;

import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;
import ui.swing.presenter.ActionPane;
import ui.swing.presenter.Presenter;
import ui.swing.presenter.UIAction;
import ui.swing.presenter.ValidationException;
import ui.swing.utils.UIEventsExecutor;
import basic.Delegate;

public class JiraIssueStatusActionPane implements ActionPane, UIAction, Startable {

	private final StartTaskDelegate startTaskDelegate;
	private final JiraIssueStatusManagement jiraIssueStatus;
	private final JiraOptions jiraOptions;
	private final Presenter presenter;
	private List<RemoteJiraIssue> issues;
	private JXTable table;
	private HashSet<RemoteJiraIssue> processedIssues;

	public JiraIssueStatusActionPane(StartTaskDelegate startTaskDelegate, JiraIssueStatusManagement jiraIssueStatus,
			JiraOptions jiraOptions, Presenter presenter, UIEventsExecutor executor) {
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
			RemoteJiraIssue issue = model.getIssueAt(row);
			if(processedIssues.contains(issue))
				continue;
			
			JiraAction action = model.getActionAt(row);
			String comment = model.getCommentAt(row);
			
			if (action != null)
				jiraIssueStatus.progressIssue(issue, action, comment);

			if (model.isChangeAssignmentChecked(row))
				jiraIssueStatus.assignToCurrentUser(issue);
			
			processedIssues.add(issue);
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
		table = new JXTable(new JiraIssueTableModel(issues));

		int lineHeight = 20;
		int keyWidth = 60;
		int summaryWidth = 230;
		int actionsWidth = 100;
		int assignToMeWidth = 100;
		int scrollWidth = keyWidth + summaryWidth + actionsWidth + actionsWidth + assignToMeWidth + summaryWidth + 10;

		table.setRowHeight(lineHeight);
		table.setIntercellSpacing(new Dimension(5, 1));

		table.getColumnModel().getColumn(JiraIssueTableModel.COLUMN_ACTIONS).setCellEditor(new JiraActionCellEditor());
		table.getColumnModel().getColumn(0).setPreferredWidth(keyWidth);
		table.getColumnModel().getColumn(0).setMaxWidth(keyWidth);
		table.getColumnModel().getColumn(1).setPreferredWidth(summaryWidth);
		table.getColumnModel().getColumn(2).setPreferredWidth(actionsWidth);
		table.getColumnModel().getColumn(2).setMaxWidth(actionsWidth);
		table.getColumnModel().getColumn(4).setPreferredWidth(actionsWidth);
		table.getColumnModel().getColumn(4).setMaxWidth(actionsWidth);
		table.getColumnModel().getColumn(JiraIssueTableModel.COLUMN_ACTIONS).setPreferredWidth(actionsWidth);
		table.getColumnModel().getColumn(JiraIssueTableModel.COLUMN_ACTIONS).setMaxWidth(actionsWidth);
		table.getColumnModel().getColumn(JiraIssueTableModel.COLUMN_ASSIGN_TO_ME).setPreferredWidth(assignToMeWidth);
		table.getColumnModel().getColumn(JiraIssueTableModel.COLUMN_ASSIGN_TO_ME).setMaxWidth(assignToMeWidth);
		table.getColumnModel().getColumn(JiraIssueTableModel.COLUMN_COMMENT).setPreferredWidth(summaryWidth);

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
		processedIssues = new HashSet<RemoteJiraIssue>();		
		presenter.showOkCancelDialog(this, "Issue Actions");
	}

	@Override
	public void stop() {
	}

	private void showIssuesForUpdateIfAny(StartTaskData startTaskData) {		
		JiraIssue jiraIssue = startTaskData.taskData().getJiraIssue();
		
		if (jiraIssue == null)
			return;

		if (!jiraOptions.isJiraEnabled() || !jiraOptions.isIssueStatusManagementEnabled())
			return;
		
		List<RemoteJiraIssue> issuesForUpdate = jiraIssueStatus.issuesForUpdate(jiraIssue);
		if(!issuesForUpdate.isEmpty())
			show(issuesForUpdate);
	}
}
