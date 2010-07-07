package ui.swing.mainScreen.tasks.summary;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.StringValue;
import org.picocontainer.Startable;

import statistics.SummaryItem;
import statistics.TaskSummarizer;
import tasks.tasks.TasksView;
import ui.swing.mainScreen.TaskList;
import ui.swing.utils.SimpleInternalFrame;
import basic.Subscriber;

public class SummaryScreen extends SimpleInternalFrame implements Startable {

	private static final long serialVersionUID = 1L;
	private static final String panelTitle = "Tasks Summary";
	private final TaskSummarizer summarizer;

	private final SummaryTableModel summaryModel;
	private JRadioButton groupByMonthRadio;
	private final TasksView tasks;
	private JRadioButton groupByYearRadio;
	private final SummaryHoursFormat summaryHoursFormat;

	public SummaryScreen(final TaskList list, final TaskSummarizer summarizer,
			final TasksView tasks, final SummaryHoursFormat sumaryHoursFormat,
			final SummaryTableModel summaryTableModel) {
		super(SummaryScreen.panelTitle);
		this.summarizer = summarizer;
		this.tasks = tasks;
		this.summaryHoursFormat = sumaryHoursFormat;
		summaryModel = summaryTableModel;
		initialize();
	}

	public void setItems(final List<SummaryItem> items) {
		this.summaryModel.setItems(items);
	}

	private void initialize() {
		add(getTopPannel(), BorderLayout.NORTH);
		add(getSummaryTable(), BorderLayout.CENTER);

		summaryHoursFormat.addChangeListener(new Subscriber() {
			@Override
			public void fire() {
				summaryModel.fireTableDataChanged();
			}
		});
	}

	private JPanel getTopPannel() {
		final JPanel panel = new JPanel(new BorderLayout());

		panel.add(getGroupPannel(), BorderLayout.CENTER);
		panel.add(getDateFormatCombo(), BorderLayout.EAST);

		return panel;
	}

	private JPanel getDateFormatCombo() {

		final JPanel panel = new JPanel();
		panel.add(new JLabel("Format:"));
		panel.add(summaryHoursFormat.getCombo());

		return panel;
	}

	private JPanel getGroupPannel() {
		final JPanel pannel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		createGroupByMonthRadio();
		createGroupByDayRadio();
		groupButtons();

		pannel.add(new JLabel("Group by:"));
		pannel.add(groupByMonthRadio);
		pannel.add(groupByYearRadio);
		return pannel;
	}

	private void groupButtons() {
		final ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(groupByMonthRadio);
		buttonGroup.add(groupByYearRadio);
	}

	private void createGroupByDayRadio() {
		final JRadioButton byDay = new JRadioButton("day");
		byDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				showSummaryPerDay();
			}
		});
		groupByYearRadio = byDay;
	}

	private void createGroupByMonthRadio() {
		groupByMonthRadio = new JRadioButton("month");
		groupByMonthRadio.setSelected(true);
		groupByMonthRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				showSummaryPerMonth();
			}
		});
	}

	private Component getSummaryTable() {

		final JXTable table = new JXTable(this.summaryModel);
		adjustColumnsAppearance(table);
		table.setSortable(false);

		return new JScrollPane(table);
	}

	private void adjustColumnsAppearance(final JXTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100);
		columnModel.getColumn(0).setMaxWidth(250);
		columnModel.getColumn(1).setPreferredWidth(150);
		columnModel.getColumn(2).setMaxWidth(100);

		columnModel.getColumn(2).setCellRenderer(
				new DefaultTableRenderer(StringValue.TO_STRING,
						SwingConstants.RIGHT));
	}

	@Override
	public void start() {
		showSummaryPerMonth();
	}

	private void showSummaryPerDay() {
		setItems(summarizer.summarizePerDay(tasks.tasks()));
	}

	private void showSummaryPerMonth() {
		setItems(summarizer.summarizePerMonth(tasks.tasks()));
	}

	@Override
	public void stop() {
	}

	public void refrescate() {
		groupByMonthRadio.setSelected(true);
		showSummaryPerMonth();
	}
}
