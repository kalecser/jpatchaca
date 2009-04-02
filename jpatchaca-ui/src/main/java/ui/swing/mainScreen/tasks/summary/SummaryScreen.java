package ui.swing.mainScreen.tasks.summary;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.StringValue;
import org.picocontainer.Startable;

import statistics.SummaryItem;
import statistics.TaskSummarizer;
import tasks.TasksSystem;
import ui.swing.mainScreen.TaskList;
import ui.swing.utils.SimpleInternalFrame;

public class SummaryScreen extends SimpleInternalFrame implements Startable{

	private static final long serialVersionUID = 1L;
	private static final String panelTitle = "Tasks Summary";
	private final TaskSummarizer summarizer;

	private SummaryTableModel summaryModel;
	private final TasksSystem tasksSystem;
	private JRadioButton groupByMonthRadio;

	public SummaryScreen(TaskList list, TaskSummarizer summarizer, TasksSystem tasksSystem) {
		super(SummaryScreen.panelTitle);
		this.summarizer = summarizer;
		this.tasksSystem = tasksSystem;
		initialize();
	}
	
	public void setItems(List<SummaryItem> items) {
		this.summaryModel.setItems(items);		
	}	

	private void initialize() {
		add(getGroupPannel(), BorderLayout.NORTH);
		add(getSummaryTable(), BorderLayout.CENTER);
	}

	private JPanel getGroupPannel() {
		JPanel pannel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pannel.add(new JLabel("Group by:"));
		
		groupByMonthRadio = new JRadioButton("month");
		groupByMonthRadio.setSelected(true);
		groupByMonthRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSummaryPerMonth();
			}
		});
		
		JRadioButton byYear = new JRadioButton("day");
		byYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSummaryPerDay();
			}
		});
		
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(groupByMonthRadio);
		buttonGroup.add(byYear);
		
		pannel.add(groupByMonthRadio);
		pannel.add(byYear);
		return pannel;
	}		

	private Component getSummaryTable() { 
		this.summaryModel = new SummaryTableModel();
		
		final JXTable table = new JXTable(this.summaryModel);
		adjustColumnsAppearance(table);
		table.setSortable(false);
		
		return new JScrollPane(table);
	}

	private void adjustColumnsAppearance(JXTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100);
		columnModel.getColumn(0).setMaxWidth(250);
		columnModel.getColumn(1).setPreferredWidth(150);
		columnModel.getColumn(2).setMaxWidth(100);
		
		columnModel.getColumn(2).setCellRenderer(new DefaultTableRenderer( StringValue.TO_STRING, SwingConstants.RIGHT));
	}
	
	@SuppressWarnings({"serial"})
	private static class SummaryTableModel extends AbstractTableModel{
		private List<SummaryItem> items = new ArrayList<SummaryItem>();
		

		public int getRowCount() {
			return this.items.size();
		}

		public int getColumnCount() {
			return 3;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			final SummaryItem item = this.items.get(rowIndex);
			
			if (columnIndex == 0) {
				return item.getFormatedDate();
			}
			
			if (columnIndex == 1) {
				return item.taskName();
			}
			
			if (columnIndex == 2) {
				final NumberFormat format = new DecimalFormat("#0.0");
				return format.format(item.hours());
			}
			
			throw new RuntimeException("columnIndex out of bounds");
		}
		
		@Override
		public String getColumnName(int column) {
			return new String[]{"Date", "Task", "Hours"}[column];
		}

		public final void setItems(List<SummaryItem> items) {
			this.items = items;
			fireTableDataChanged();
		}
	}

	@Override
	public void start() {
		showSummaryPerMonth();
	}

	private void showSummaryPerDay() {
		setItems(summarizer.summarizePerDay(tasksSystem.tasks()));		
	}

	private void showSummaryPerMonth() {
		setItems(summarizer.summarizePerMonth(tasksSystem.tasks()));
	}

	@Override
	public void stop() {
	}

	public void refrescate() {
		groupByMonthRadio.setSelected(true);
		showSummaryPerMonth();
	}
}
