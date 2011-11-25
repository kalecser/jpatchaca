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
import jira.JiraSystem;
import jira.JiraWorklogOverride;

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
import basic.Formatter;
import basic.HardwareClock;

public class DayTasksList extends SimpleInternalFrame implements Startable {

	private static final long serialVersionUID = 1L;
	private static final String panelTitle = "Day Tasks List";

	private DayTasksTableModel dayTasksTableModel;
	private JTextField totalHoursTextField;
	private JXDatePicker datePicker;
	private final TasksSystem tasksSystem;
	private final TasksView tasks;
	private final Formatter formatter;
	private List<Pair> items = new ArrayList<Pair>();
	private final JiraOptions jiraOptions;
	private final JiraSystem jiraSystem;
	private JXTable dayTasksTable;
	private final HardwareClock clock;
	private final JiraWorklogOverride worklogOverride;

	public DayTasksList(final TasksView tasks, final Formatter formatter,
			final TasksSystem tasksSystem, final JiraOptions jiraOptions,
			final JiraSystem jiraSystem,
			HardwareClock clock, JiraWorklogOverride worklogOverride) {
		
		super(DayTasksList.panelTitle);
		this.tasks = tasks;
		this.formatter = formatter;
		this.tasksSystem = tasksSystem;
		this.jiraOptions = jiraOptions;
		this.jiraSystem = jiraSystem;
		this.clock = clock;
		this.worklogOverride = worklogOverride;
		initialize();
	}

	public void setItems(final List<Pair> items) {
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

				showTasksByDay(picker.getDate());

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
		dayTasksTableModel = new DayTasksTableModel(formatter, tasksSystem,
				worklogOverride);

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
		showTasksByDay(new Date());
	}

	void showTasksByDay(final Date data) {
		final List<Pair> lista = new ArrayList<Pair>();
		
		for (final TaskView task : tasks.tasks())
			for (final Period period : task.periods())
				if (periodoDentroDoDia(data, period))
					lista.add(new Pair(task, period, formatter, worklogOverride));

		Collections.sort(lista);
		setItems(lista);
	}

	void sendWorklog() {
		final List<Pair> tasksPeriods = new LinkedList<Pair>();

		if (dayTasksTable.getSelectedRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "No periods selected");
			return;
		}

		for (final int i : dayTasksTable.getSelectedRows()) {
			final Pair pair = items.get(i);
			if (pair.task().getJiraIssue() != null
					&& !pair.period().isWorklogSent()) {
				tasksPeriods.add(pair);
			}
		}

		if (tasksPeriods.size() == 0) {
			return;
		}

		final StringBuilder builder = new StringBuilder();
		builder.append("Sending worklog for selected periods as ");
		builder.append("'");
		builder.append(jiraOptions.getUserName());
		builder.append("'");

		final int opt = JOptionPane.showConfirmDialog(this, builder.toString(),
				"Send worklog", JOptionPane.YES_OPTION
						| JOptionPane.CANCEL_OPTION);

		if (opt == JOptionPane.YES_OPTION) {
			for (final Pair pair : tasksPeriods) {
				jiraSystem.addWorklog(pair.task(), pair.period());
				dayTasksTableModel.fireTableDataChanged();
			}
		}
	}

	private String getDayTotalHours(final List<Pair> lista) {
		double totalHours = 0;

		for (final Pair par : lista) 
			totalHours += par.period().getMiliseconds();

		final NumberFormat format = new DecimalFormat("#0.00");
		return format.format(totalHours / DateUtils.MILLIS_PER_HOUR);
	}

	private boolean periodoDentroDoDia(final Date data, final Period period) {
		return period.getDay().equals(getDay(data));
	}

	private Date getDay(final Date date) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);

		return cal.getTime();
	}

	@Override
	public void stop() {
		// Do nothing
	}

	public void refrescate() {
		datePicker.setDate(clock.getTime());
		showTasksByDay(datePicker.getDate());
	}

}
