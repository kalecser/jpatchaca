package ui.swing.mainScreen.periods;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.TablecellBalloonTip;
import net.java.balloontip.styles.RoundedBalloonStyle;
import net.java.balloontip.utils.TimingUtils;

import org.reactive.Receiver;

import periods.Period;
import periodsInTasks.PeriodsInTasksSystem;
import sun.swing.SwingUtilities2;
import tasks.TaskView;
import tasks.tasks.Tasks;
import ui.swing.Icons;
import ui.swing.mainScreen.dragAndDrop.PeriodTransferable;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.utils.SimpleInternalFrame;
import ui.swing.utils.SwingUtils;
import basic.HardwareClock;

@SuppressWarnings( { "serial", "restriction" })
public class PeriodsList extends SimpleInternalFrame {

	private PeriodsTable periodsTable;
	private JButton addPeriodButton;
	private JButton removePeriodButton;
	private final SelectedTaskSource selectedTaskSource;
	private final PeriodsTableModel periodsTableModel;
	private final PeriodsInTasksSystem periodsSystem;
	protected final PeriodsListModel model;
	private final HardwareClock machineClock;
	private final RemovePeriodsDialogController removePeriodsDialogController;
	private final PeriodsTableWhiteboard periodsWhiteboard;
	private final Tasks tasks;
	private JButton mergePeriodsButton;

	public PeriodsList(
			final SelectedTaskSource selectedTaskSource,
			final PeriodsInTasksSystem periodsInTasks,
			final PeriodsTableModel periodsTableModel,
			final HardwareClock machineClock,
			final RemovePeriodsDialogController removePeriodsDialogController,
			final PeriodsTableWhiteboard periodsWhiteboard, Tasks tasks) {

		super("Selected task's periods");
		this.selectedTaskSource = selectedTaskSource;
		this.periodsSystem = periodsInTasks;
		this.periodsTableModel = periodsTableModel;
		this.machineClock = machineClock;
		this.removePeriodsDialogController = removePeriodsDialogController;
		this.periodsWhiteboard = periodsWhiteboard;
		this.tasks = tasks;
		this.model = new PeriodsListModel(periodsSystem, selectedTaskSource);

		initializeInSwingThread();

	}

	public int selectedPeriodIndex(){
		
		List<Period> selectedPeriods = periodsTable.selectedPeriods();
		
		if (selectedPeriods.size() == 0)
			return -1;
			
		Period firstPeriod = selectedPeriods.get(0);
		return model.selectedPeriodIndex(firstPeriod);
	}


	
	private void initializeInSwingThread() {
		SwingUtils.invokeAndWaitOrCry(new Runnable() {@Override public void run() {
			initialize();
		}});
		
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

		addPeriodButton = new JButton(Icons.ADD_ICON);
		addPeriodButton.setToolTipText("Add period");
		
		removePeriodButton = new JButton(Icons.REMOVE_ICON);
		removePeriodButton.setToolTipText("Delete period");
		
		mergePeriodsButton = new JButton(Icons.MERGE_ICON);
		mergePeriodsButton.setBorder(BorderFactory.createEmptyBorder());
		mergePeriodsButton.setToolTipText("Merge periods");
		
		
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
						removePeriods();
					}
				});

		toolBar.add(addPeriodButton);
		toolBar.add(removePeriodButton);
		//toolBar.add(mergePeriodsButton);
		return toolBar;
	}

	private JScrollPane getPeriodsTable() {
		this.periodsTable = new PeriodsTable(this.periodsTableModel);
		final JScrollPane scrollPane = new JScrollPane(this.periodsTable);

		bindPeriodsTableToWhiteboard(periodsTable, scrollPane);

		removePeriodsWithDelKey();

		configureDragAndDropStuff();

		return scrollPane;
	}

	private void removePeriodsWithDelKey() {
		periodsTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					removePeriods();
				}
			}
		});
	}

	private void bindPeriodsTableToWhiteboard(final JTable table,
			final JScrollPane scrollPane) {

		periodsWhiteboard.messageBoard().addReceiver(new Receiver<String>() {
			TablecellBalloonTip tablecellBalloonTip;

			@Override
			public void receive(final String value) {
				if (tablecellBalloonTip != null) {
					hideTableCellBalloonTipInSwingThread();
				}

				if (value == null || value.equals("")) {
					return;
				}

				tablecellBalloonTip = showBallonTipInSwingThread(value);

			}

			private TablecellBalloonTip showBallonTipInSwingThread(
					final String value) {

				final Future<TablecellBalloonTip> future = SwingUtilities2
						.submit(new Callable<TablecellBalloonTip>() {

							@Override
							public TablecellBalloonTip call() throws Exception {
								final TablecellBalloonTip tableBalloon = new TablecellBalloonTip(
										table, value, table.getSelectedRow(),
										table.getSelectedColumn(),
										new RoundedBalloonStyle(5, 5,
												Color.WHITE, Color.BLACK),
										BalloonTip.Orientation.LEFT_ABOVE,
										BalloonTip.AttachLocation.ALIGNED, 40,
										20, true);
								tableBalloon.setViewport(scrollPane
										.getViewport());

								final int fiveSeconds = 5000;
								TimingUtils.showTimedBalloon(tableBalloon,
										fiveSeconds);

								return tableBalloon;
							}

						});

				try {
					return future.get();
				} catch (final InterruptedException e) {
					throw new RuntimeException(e);
				} catch (final ExecutionException e) {
					throw new RuntimeException(e);
				}

			}

			private void hideTableCellBalloonTipInSwingThread() {

				final Runnable hide = new Runnable() {
					public void run() {
						tablecellBalloonTip.setVisible(false);
					}
				};

				SwingUtils.invokeAndWaitOrCry(hide);
			}
		});
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
				String idOfSelectedTask = tasks.idOf(selectedTaskSource.currentValue()).getId();
				return new PeriodTransferable(idOfSelectedTask,  selectedPeriodIndex());
			}

		});
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

				final Period newPeriod = new Period(machineClock.getTime());
				periodsSystem.addPeriod(selectedTask, newPeriod);

			}
		});
	}

	private void removePeriods() {
		final List<Period> selectedPeriods = periodsTable.selectedPeriods();

		if (selectedPeriods.isEmpty()) {
			PeriodsLogger.logger().error(
					"Trying to remove periods with none selected");
			return;
		}

		removePeriodsDialogController.confirmPeriodsRemoval(selectedPeriods);
	}

}
