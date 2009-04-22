package ui.swing.mainScreen;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lang.Maybe;

import org.reactive.Receiver;

import tasks.ActiveTask;
import tasks.tasks.Task;
import tasks.tasks.TaskView;
import ui.swing.mainScreen.dragAndDrop.TaskTransferable;
import ui.swing.mainScreen.tasks.TaskSelectionListener;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.utils.SimpleInternalFrame;
import wheel.io.files.Directory;
import basic.Alert;
import basic.AlertImpl;
import basic.DeferredExecutor;

@SuppressWarnings("serial")
public class TaskList extends JPanel {

	public class ActiveTaskChanged implements Receiver<Maybe<Task>> {

		@Override
		public void receive(final Maybe<Task> pulse) {
			final Maybe<Task> maybeActiveTask = pulse;

			if (maybeActiveTask == null) {
				return;
			}

			setSelectedTask(maybeActiveTask.unbox());

		}
	}

	private final TaskListListModel tasksListModel;
	private final JList tasksList;
	private final List<TaskSelectionListener> listeners;
	private final LabelsList labelsList;
	private final AlertImpl selectedLabelChanged;
	private final AlertImpl movePeriodAlert;

	private TaskView dropTargetTask;
	private final TaskContextMenu taskContextMenu;

	private final TasksListData screenData;
	private final TaskListMemory memory;

	Runnable fireChangeListeners = new Runnable() {

		public void run() {
			for (final TaskSelectionListener listener : listeners) {
				listener.selectionChangedTo((TaskView) SelectedValueGetter
						.getSelectedValueInSwingThread(tasksList));
			}
		}

	};

	final DeferredExecutor executor = new DeferredExecutor(200,
			fireChangeListeners);
	private final SelectedTaskSource selectedTask;
	private final ActiveTask activeTaskSignal;

	public TaskList(final TaskListModel model, final LabelsList labelsList,
			final Directory directory, final TaskContextMenu taskContextMenu,
			final SelectedTaskSource selectedTask, final ActiveTask activeTask) {

		this.selectedTask = selectedTask;
		this.activeTaskSignal = activeTask;
		this.memory = new DeferredTaskListMemory(directory);
		this.screenData = memory.retrieve();
		this.taskContextMenu = taskContextMenu;

		this.labelsList = labelsList;

		this.selectedLabelChanged = new AlertImpl();
		movePeriodAlert = new AlertImpl();
		this.listeners = new ArrayList<TaskSelectionListener>();
		this.tasksListModel = new TaskListListModel();

		this.tasksList = newJList(this.tasksListModel, model.getTooltips());

		final JScrollPane scrolledLabelsList = new JScrollPane(labelsList);
		final JScrollPane scrolledTasksList = new JScrollPane(this.tasksList);
		scrolledLabelsList.setMinimumSize(new Dimension(0, 110));

		final JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				new SimpleInternalFrame("Labels", null, scrolledLabelsList),
				new SimpleInternalFrame("Tasks", null, scrolledTasksList));
		split.setContinuousLayout(true);

		this.setLayout(new BorderLayout());

		final JButton createTaskButton = new JButton("Start task");
		createTaskButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				model.startTask();
			}

		});
		createTaskButton.setFocusable(false);
		final JPanel createTaskPannel = new JPanel();
		createTaskPannel.setLayout(new BorderLayout());
		createTaskPannel.add(createTaskButton, BorderLayout.CENTER);
		createTaskPannel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
				10));

		this.add(createTaskPannel, BorderLayout.NORTH);
		this.add(split, BorderLayout.CENTER);
		this.setMinimumSize(new Dimension(180, 0));

		bindToLabelsList();
		bindToActiveTaskSignal();
	}

	private void bindToActiveTaskSignal() {
		activeTaskSignal.addReceiver(new ActiveTaskChanged());
	}

	private void bindToLabelsList() {
		this.labelsList.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					public void valueChanged(final ListSelectionEvent e) {

						try {
							TaskList.this.labelsList.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							TaskList.this.selectedLabelChanged.fire();
							final int selectedIndex = labelsList
									.getSelectedIndex();
							screenData.setSelectedLabel(selectedIndex);
							labelsList.setPreferredIndex(selectedIndex);
							memory.mind(screenData);
						} finally {
							TaskList.this.labelsList.setCursor(new Cursor(
									Cursor.DEFAULT_CURSOR));
						}

					}
				});

		labelsList.setPreferredIndex(screenData.getSelectedLabel());

	}

	public Alert selectedLabelChanged() {
		return this.selectedLabelChanged;
	}

	public String selectedLabel() {
		return (String) this.labelsList.getSelectedValue();
	}

	class SelectedTaskChangedListSelectionListener implements
			ListSelectionListener {

		@Override
		public void valueChanged(final ListSelectionEvent e) {
			selectedTaskChanged(e.getFirstIndex());
		}

		private void selectedTaskChanged(final int selectedIndex) {
			fireTaskChangeListeners();

			if (selectedIndex > -1) {
				screenData.setSelectedTask(selectedIndex);
				memory.mind(screenData);
			}

			selectedTask.supply(selectedTask());

		}
	}

	private JList newJList(final TaskListListModel listModel,
			final TooltipForTask tooltip) {
		final JList tasksList = new TasksJList(listModel, tooltip);

		final ListSelectionModel selectionModel = tasksList.getSelectionModel();

		selectionModel
				.addListSelectionListener(new SelectedTaskChangedListSelectionListener());

		tasksList.setCellRenderer(new TaskListCellRenderer());

		tasksList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(final MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON3) {
					final int index = tasksList.locationToIndex(e.getPoint());

					selectionModel.setSelectionInterval(index, index);
					TaskList.this.taskContextMenu.show(tasksList, e.getX(), e
							.getY(), selectedTask());
				}
			}

		});

		tasksList.setTransferHandler(new TransferHandler() {

			@Override
			public int getSourceActions(final JComponent c) {
				return COPY;
			}

			@Override
			protected Transferable createTransferable(final JComponent c) {
				return new TaskTransferable("a");
			}

			@Override
			public boolean canImport(final TransferSupport info) {
				try {
					final String data = (String) info
							.getTransferable()
								.getTransferData(DataFlavor.stringFlavor);
					return data.startsWith("period -");
				} catch (final Exception e) {
				}

				return false;
			}

			@Override
			public boolean importData(final TransferSupport info) {
				final Point point = info.getDropLocation().getDropPoint();
				final int index = tasksList.locationToIndex(point);
				dropTargetTask = (TaskView) tasksListModel.getElementAt(index);
				movePeriodAlert.fire();
				return true;
			}
		});

		tasksList.setDragEnabled(true);
		tasksList.setDropMode(DropMode.ON);

		return tasksList;
	}

	public void setTasks(final List<TaskView> tasks) {

		this.tasksListModel.setTasks(tasks);

		final boolean hasElements = this.tasksListModel.getSize() > 0;
		final boolean noneSelected = (this.tasksList.getSelectedIndex() == -1);
		if (noneSelected && hasElements) {
			this.tasksList.setSelectedIndex(screenData.getSelectedTask());
		}

		fireTaskChangeListeners();
	}

	public void addTaskSelectionListener(final TaskSelectionListener listener) {
		this.listeners.add(listener);
		fireTaskChangeListeners();
	}

	private void fireTaskChangeListeners() {
		executor.execute();
	}

	public TaskView selectedTask() {
		return (TaskView) this.tasksList.getSelectedValue();
	}

	public void setSelectedTask(final TaskView task) {
		this.tasksList.setSelectedValue(task, true);
	}

	public final Alert movePeriodAlert() {
		return movePeriodAlert;
	}

	public TaskView dropTargetTask() {
		return dropTargetTask;
	}

	public void setDropTargetTaskTesting(final TaskView dropTargetTask) {
		this.dropTargetTask = dropTargetTask;
	}

	public void doDropPeriod() {
		movePeriodAlert.fire();
	}

}
