package ui.swing.mainScreen.periods;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.StringValue;

import periods.Period;
import periods.PeriodsListener;
import periodsInTasks.PeriodsInTasksSystem;
import swing.JXTableImproved;
import tasks.TaskView;
import ui.swing.mainScreen.TaskList;
import ui.swing.mainScreen.dragAndDrop.PeriodTransferable;
import ui.swing.mainScreen.tasks.TaskSelectionListener;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.utils.SimpleInternalFrame;
import basic.Alert;
import basic.AlertImpl;
import basic.HardwareClock;
import basic.Subscriber;

@SuppressWarnings("serial")
public class PeriodsList extends SimpleInternalFrame implements
		TaskSelectionListener {

	private final AlertImpl removePeriodAlert;
	private JXTable periodsTable;
	private JButton addPeriodButton;
	private JButton removePeriodButton;
	private final SelectedTaskSource selectedTaskSource;
	private final PeriodsTableModel periodsTableModel;
	private final PeriodsListener listScrollListener;
	private TaskView _selectedTask;
	private final PeriodsInTasksSystem periodsSystem;
	protected final PeriodsListModel model;
	private final HardwareClock machineClock;

	public PeriodsList(final TaskList tasksList,
			final SelectedTaskSource selectedTaskSource,
			final PeriodsInTasksSystem periodsInTasks,
			final PeriodsTableModel periodsTableModel,
			final HardwareClock machineClock) {

		super("Selected task's periods");
		this.selectedTaskSource = selectedTaskSource;
		this.periodsSystem = periodsInTasks;
		this.periodsTableModel = periodsTableModel;
		this.machineClock = machineClock;
		this.model = new PeriodsListModel(periodsSystem);

		initialize();
		listScrollListener = listScrollListener();

		this.removePeriodAlert = new AlertImpl();

		tasksList.addTaskSelectionListener(this);

		// bug: I don't like it!
		removePeriodAlert().subscribe(new Subscriber() {

			public void fire() {
				model.removePeriod(selectedTaskSource.currentValue(),
						selectedPeriodIndex());
			}
		});

	}

	private void initialize() {
		final JScrollPane scrollPane = getPeriodsTable();
		scrollPane.setPreferredSize(new Dimension(100, 0));
		add(scrollPane, BorderLayout.CENTER);

		final JToolBar toolBar = getPeriodsToolbar();

		setToolBar(toolBar);
	}

	private JToolBar getPeriodsToolbar() {
		final JToolBar toolBar = new JToolBar();

		addPeriodButton = new JButton("add");
		removePeriodButton = new JButton("remove");

		addPeriodButton.setFocusable(false);
		removePeriodButton.setFocusable(false);

		addPeriodButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				addPeriod();
			}
		});

		removePeriodButton
				.addActionListener(new java.awt.event.ActionListener() {

					public void actionPerformed(final ActionEvent e) {
						PeriodsList.this.removePeriodAlert.fire();
					}
				});

		addPeriodButton.setToolTipText("Adds a new custom period");
		toolBar.add(addPeriodButton);
		toolBar.add(removePeriodButton);
		return toolBar;
	}

	private JScrollPane getPeriodsTable() {
		this.periodsTable = new JXTableImproved(this.periodsTableModel);
		periodsTable.setFont(Font.decode(Font.MONOSPACED));
		this.periodsTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		periodsTable.setSortable(false);

		configureDragAndDropStuff();
		adjustColumnsAppearance(this.periodsTable);

		final JScrollPane scrollPane = new JScrollPane(this.periodsTable);
		return scrollPane;
	}

	private void adjustColumnsAppearance(final JXTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		final int smallSize = 100;
		final int mediumSize = 200;
		columnModel.getColumn(0).setPreferredWidth(mediumSize);
		columnModel.getColumn(0).setMaxWidth(mediumSize);
		columnModel.getColumn(1).setPreferredWidth(smallSize);
		columnModel.getColumn(1).setMaxWidth(smallSize);
		columnModel.getColumn(2).setPreferredWidth(smallSize);
		columnModel.getColumn(2).setMaxWidth(smallSize);
		columnModel.getColumn(3).setPreferredWidth(smallSize);
		columnModel.getColumn(3).setMaxWidth(smallSize);
		columnModel.getColumn(3).setCellRenderer(
				new DefaultTableRenderer(StringValue.TO_STRING,
						SwingConstants.RIGHT));
	}

	private void configureDragAndDropStuff() {
		this.periodsTable.setDragEnabled(true);

		this.periodsTable.setTransferHandler(new TransferHandler() {

			@Override
			public int getSourceActions(final JComponent arg0) {
				return MOVE;
			}

			@Override
			protected Transferable createTransferable(final JComponent arg0) {
				return new PeriodTransferable(periodsTable.getSelectedRow());
			}

		});
	}

	public void selectionChangedTo(final TaskView selectedTask) {
		if (_selectedTask != null) {
			_selectedTask.removePeriodListener(listScrollListener);
		}

		this._selectedTask = selectedTask;

		if (_selectedTask == null) {
			return;
		}

		selectAndScrollToRow(0);
		selectedTask.addPeriodsListener(listScrollListener);
	}

	private PeriodsListener listScrollListener() {
		return new PeriodsListener() {

			@Override
			public void periodAdded(final Period period) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						selectAndScrollToRow(0);
					}
				});
			}

			@Override
			public void periodRemoved(final Period period) {
			}

			@Override
			public void clean() {
			}

		};
	}

	void selectAndScrollToRow(final int row) {
		this.periodsTable.getSelectionModel().setSelectionInterval(row, row);
		this.periodsTable.scrollRectToVisible(this.periodsTable.getCellRect(
				row, 0, true));
	}

	public Period selectedPeriod() {
		final int selectedRow = this.periodsTable.getSelectedRow();
		if (selectedRow == -1) {
			return null;
		}
		return this.periodsTableModel.getPeriodAt(selectedRow);
	}

	public void setSelectedPeriodTesting(final int index) {
		this.periodsTable.getSelectionModel()
				.setSelectionInterval(index, index);

	}

	public void clickOnAddPediodButton() {
		addPeriodButton.doClick();
	}

	public Alert removePeriodAlert() {
		return removePeriodAlert;
	}

	private void addPeriod() {
		try {
			addPeriodButton.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			enqueueAddPeriod();
		} finally {
			addPeriodButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

	}

	private void enqueueAddPeriod() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				final TaskView selectedTask = selectedTaskSource.currentValue();
				new Thread() {

					@Override
					public void run() {
						final Period newPeriod = new Period(machineClock
								.getTime());
						periodsSystem.addPeriod(selectedTask, newPeriod);
					}
				}.start();
			}
		});
	}

	public int selectedPeriodIndex() {
		final TaskView selectedTask = selectedTaskSource.currentValue();
		return selectedTask.periods().indexOf(selectedPeriod());
	}

}
