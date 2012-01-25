package ui.swing.mainScreen.tasks.day;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumnModel;

import jira.JiraOptions;

import org.apache.commons.lang.time.DateUtils;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.StringValue;
import org.picocontainer.Startable;

import periods.Period;
import tasks.TaskView;
import tasks.TasksSystem;
import tasks.tasks.TasksView;
import ui.swing.utils.SimpleInternalFrame;
import basic.HardwareClock;

public class DayTasksList extends SimpleInternalFrame implements Startable {

	private static final long serialVersionUID = 1L;
	private static final String panelTitle = "Day Tasks List";

	private DayTasksTableModel dayTasksTableModel;
	private JTextField totalHoursTextField;
	private JXDatePicker datePicker;
	private final TasksView tasks;
	private List<TaskWorklog> items = new ArrayList<TaskWorklog>();
	private final JiraOptions jiraOptions;
	private JXTable dayTasksTable;
	private final HardwareClock clock;
	private final TaskWorklogFactory worklogFactory;
	private final DayTaskListModel dayTaskListModel;

	public DayTasksList(final TasksView tasks, final TasksSystem tasksSystem,
			final JiraOptions jiraOptions, HardwareClock clock, TaskWorklogFactory worklogFactory, DayTaskListModel dayTaskListModel) {

		super(DayTasksList.panelTitle);
		this.tasks = tasks;
		this.jiraOptions = jiraOptions;
		this.clock = clock;
		this.worklogFactory = worklogFactory;
		this.dayTaskListModel = dayTaskListModel;
		initialize();
	}

	public void setItems(final List<TaskWorklog> items) {
		this.items = items;
		dayTasksTableModel.setItems(this.items);
		updateTotalHours();
	}

	void updateTotalHours() {
		totalHoursTextField.setText(getDayTotalHours(items));
	}

	private void initialize() {
		add(getGroupPannel(), BorderLayout.NORTH);
		add(getSummaryTable(), BorderLayout.CENTER);
	}

	private JPanel getGroupPannel() {
		final JPanel pannel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pannel.add(new JLabel("Day: "));

		datePicker = new JXDatePicker(new Date());
		datePicker.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final JXDatePicker picker = (JXDatePicker) e.getSource();

				setDay(picker.getDate());
//				showTasksByDay(picker.getDate());

			}			
		});

		pannel.add(datePicker);

		pannel.add(new JLabel("Total hours: "));

		totalHoursTextField = new JTextField(5);
		totalHoursTextField.setEditable(false);
		totalHoursTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		pannel.add(totalHoursTextField);

		final JButton sendWorkLogButton = new JButton("Send Worklog");
		sendWorkLogButton.setHorizontalAlignment(SwingConstants.LEFT);
		sendWorkLogButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				sendWorklog();
			}
		});

		final JPanel wlogPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		wlogPanel.add(sendWorkLogButton);

		final JPanel retorno = new JPanel(new BorderLayout());
		retorno.add(pannel, BorderLayout.WEST);
		retorno.add(wlogPanel, BorderLayout.EAST);

		return retorno;
	}

	private Component getSummaryTable() {
		dayTasksTableModel = new DayTasksTableModel();

		dayTasksTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(final TableModelEvent e) {
				updateTotalHours();
			}

		});

		dayTasksTable = new JXTable(dayTasksTableModel);
		adjustColumnsAppearance(dayTasksTable);
		dayTasksTable.setSortable(true);

		return new JScrollPane(dayTasksTable);
	}

	private void adjustColumnsAppearance(final JXTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(150);
		columnModel.getColumn(1).setPreferredWidth(100);
		columnModel.getColumn(1).setMaxWidth(100);
		columnModel.getColumn(2).setPreferredWidth(100);
		columnModel.getColumn(2).setMaxWidth(100);
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(3).setMaxWidth(100);
		columnModel.getColumn(4).setPreferredWidth(100);
		columnModel.getColumn(4).setMaxWidth(150);
		columnModel.getColumn(5).setPreferredWidth(100);
		columnModel.getColumn(5).setMaxWidth(150);

		columnModel.getColumn(4).setCellRenderer(
				new DefaultTableRenderer(StringValue.TO_STRING,
						SwingConstants.RIGHT));
		columnModel.getColumn(5).setCellRenderer(
				new DefaultTableRenderer(StringValue.TO_STRING,
						SwingConstants.RIGHT));
	}

	@Override
	public void start() {
		setDay(new Date());		
	}

	void showTasks() {
		final List<TaskWorklog> lista = dayTaskListModel.worklogs();
		Collections.sort(lista);
		setItems(lista);
	}
	
	private void setDay(Date date) {
		dayTaskListModel.setDay(date);
		showTasks();
	}
	
	void sendWorklog() {
		final List<TaskWorklog> tasksPeriods = worklogsToSend();
		if (tasksPeriods.size() == 0) {
			JOptionPane.showMessageDialog(this, "No periods selected");
			return;
		}

		if (confirmSendWorklog())
			dayTaskListModel.sendWorklog();
			for (final TaskWorklog worklog : tasksPeriods)
				if (worklog.canSend()) {
					worklog.send();
					dayTasksTableModel.fireTableDataChanged();
				}
	}

	private List<TaskWorklog> worklogsToSend() {
		final List<TaskWorklog> tasksPeriods = new LinkedList<TaskWorklog>();
		for (final int i : dayTasksTable.getSelectedRows())
			tasksPeriods.add(items.get(i));
		return tasksPeriods;
	}

	private boolean confirmSendWorklog() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Sending worklog for selected periods as ");
		builder.append("'");
		builder.append(jiraOptions.getUserName());
		builder.append("'");

		final int opt = JOptionPane.showConfirmDialog(this, builder.toString(),
				"Send worklog", JOptionPane.YES_OPTION
						| JOptionPane.CANCEL_OPTION);

		boolean confirmSendWorklog = opt == JOptionPane.YES_OPTION;
		return confirmSendWorklog;
	}

	private String getDayTotalHours(final List<TaskWorklog> lista) {
		double totalHours = 0;

		for (final TaskWorklog worklog : lista)
			totalHours += worklog.getMiliseconds();

		final NumberFormat format = new DecimalFormat("#0.00");
		return format.format(totalHours / DateUtils.MILLIS_PER_HOUR);
	}

	@Override
	public void stop() {
		// Do nothing
	}

	public void refrescate() {
		datePicker.setDate(clock.getTime());		
		//showTasksByDay(datePicker.getDate());
	}

}
