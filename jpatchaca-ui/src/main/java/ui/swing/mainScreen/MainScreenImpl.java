package ui.swing.mainScreen;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.picocontainer.Startable;
import org.reactivebricks.pulses.Receiver;

import ui.swing.mainScreen.periods.PeriodsList;
import ui.swing.mainScreen.tasks.summary.SummaryScreen;
import ui.swing.utils.UIEventsExecutor;
import wheel.io.ui.JFrameBoundsKeeper;

@SuppressWarnings("serial")
public class MainScreenImpl extends JFrame implements MainScreen, Startable {

	final class TopBarListener implements TopBar.Listener {

		@Override
		public void stopTask() {
			model.stopSelectedTask();
		}

		@Override
		public void startTask() {
			model.showStartTaskScreen();
		}

		@Override
		public void removeTask() {
			model.removeSelectedTask();
		}

		@Override
		public void editTask() {
			model.editSelectedTask();
		}

		@Override
		public void createTask() {
			model.showCreateTaskScreen();
		}

		@Override
		public void options() {
			model.showOptionsScreen();
		}
	}

	final MainScreenModel model;
	private final TaskList taskList;
	private final PeriodsList periodsList;
	private final SummaryScreen tasksSummary;
	private final TopBar topBar;

	public MainScreenImpl(final MainScreenModel model,
			final UIEventsExecutor executor, final TaskList taskList,
			final PeriodsList periodsList, final SummaryScreen tasksSummary,
			final JFrameBoundsKeeper boundsKeeper) {

		this.model = model;
		this.taskList = taskList;
		this.periodsList = periodsList;
		this.topBar = new TopBar(executor);
		this.tasksSummary = tasksSummary;

		boundsKeeper.keepBoundsFor(this, MainScreenImpl.class.getName());
	}

	private void initialize() {
		setBounds(150, 150, 500, 350);

		bindTopBar();

		setLayout(new BorderLayout());
		add(topBar, BorderLayout.NORTH);
		add(getCenterPane(), BorderLayout.CENTER);
		pack();

		class TitleReceiver implements Receiver<String> {

			@Override
			public void receive(final String pulse) {
				setTitle(pulse);
			}

		}
		model.titleSignal().addReceiver(new TitleReceiver());
	}

	private void bindTopBar() {
		topBar.addListener(new TopBarListener());
	}

	private JSplitPane getCenterPane() {
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Periods", this.periodsList);
		tabbedPane.add("Summary", this.tasksSummary);

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent e) {
				refrescateTasksSummary();
			}
		});

		tabbedPane.setSelectedIndex(0);

		final JSplitPane center = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				this.taskList, tabbedPane);
		center.setContinuousLayout(true);
		return center;
	}

	@Override
	public Window getWindow() {
		return this;
	}

	@Override
	public Window owner() {
		return this;
	}

	@Override
	public void start() {
		initialize();
	}

	@Override
	public void stop() {
		// Nothing special.
	}

	void refrescateTasksSummary() {
		tasksSummary.refrescate();
	}

}
