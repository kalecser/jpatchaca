package ui.swing.mainScreen;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.picocontainer.Startable;
import org.reactivebricks.pulses.Pulse;
import org.reactivebricks.pulses.Receiver;

import tasks.TasksSystem;
import tasks.delegates.StartTaskDelegate;
import ui.swing.mainScreen.periods.PeriodsList;
import ui.swing.mainScreen.tasks.TaskScreenController;
import ui.swing.mainScreen.tasks.summary.SummaryScreen;
import ui.swing.options.OptionsScreen;
import ui.swing.presenter.Presenter;
import ui.swing.users.SwingTasksUser;
import version.PatchacaVersion;
import wheel.io.ui.JFrameBoundsKeeper;
import events.EventsSystem;

@SuppressWarnings("serial")
public class MainScreenImpl extends JFrame implements MainScreen, Startable {
	private final TaskList taskList;
	private final PeriodsList periodsList;
	private final SummaryScreen tasksSummary;
	private final EventsSystem eventsSystem;
	private final TopBar topBar;
	private final SwingTasksUser tasksUser;
	private final TasksSystem tasksSystem;
	private final StartTaskDelegate startTaskDelegate;
	private final OptionsScreen optionsScreen;
	private final TaskScreenController taskScreen;

	public MainScreenImpl( EventsSystem eventsSystem, TaskList taskList, PeriodsList periodsList, TopBar topBar, SummaryScreen tasksSummary, JFrameBoundsKeeper boundsKeeper, SwingTasksUser taskUser, TasksSystem tasksSystem, StartTaskDelegate startTaskDelegate, OptionsScreen optionsScreen, TaskScreenController taskScreen, Presenter presenter){
		this.eventsSystem = eventsSystem;
		this.taskList = taskList;
		this.periodsList = periodsList;
		this.topBar = topBar;
		this.tasksSummary = tasksSummary;
		this.tasksUser = taskUser;
		this.tasksSystem = tasksSystem;
		this.startTaskDelegate = startTaskDelegate;
		this.optionsScreen = optionsScreen;
		this.taskScreen = taskScreen;
		presenter.setMainScreen(this);
		
		boundsKeeper.keepBoundsFor(this, MainScreenImpl.class.getName());	
		
	}

	private void initialize() {
		
		updateTitle("");
		
		setBounds(150,150,500,350);
		
		bindTopBar();
		
		setLayout(new BorderLayout());
		add(topBar, BorderLayout.NORTH);
		add(getCenterPane(), BorderLayout.CENTER);
		pack();
		
		tasksSystem.activeTaskNameSignal().addReceiver(new Receiver<String>() {
			@Override
			public void receive(Pulse<String> pulse) {
				updateTitle(pulse.value());		
			}
		});
		
	}

	private void updateTitle(String activeTask) {
		if (tasksSystem.activeTask() != null)
			setTitle(getTitleString(tasksSystem.activeTaskName()));
		else
			setTitle(getTitleString());
	}

	private String getTitleString() {
		return "Patchaca tracker 2, version: " + PatchacaVersion.getVersion() + ", events: " + eventsSystem.getEventCount();
	}

	private String getTitleString(String activeTaskName) {
		return getTitleString() + ", Active task: " + activeTaskName;
	}

	private void bindTopBar() {
		topBar.addListener(new TopBar.Listener() {
		
			@Override
			public void stopTask() {
				tasksSystem.stopTask(taskList.selectedTask());
			}
		
			@Override
			public void startTask() {
				startTaskDelegate.execute(taskList.selectedTask());
			}
		
			@Override
			public void removeTask() {
				if (tasksUser.isTaskExclusionConfirmed())
					tasksSystem.removeTask(taskList.selectedTask());	
		
			}
		
			@Override
			public void editTask() {
				taskScreen.editSelectedTask();	
		
			}
		
			@Override
			public void createTask() {
				taskScreen.createTask();
			}

			@Override
			public void options() {
				optionsScreen.show();				
			}
		
		});
		
	}

	private JSplitPane getCenterPane() {
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Periods", this.periodsList);
		tabbedPane.add("Summary", this.tasksSummary);
		
		tabbedPane.addChangeListener(new ChangeListener() {
		
			@Override
			public void stateChanged(ChangeEvent e) {
				tasksSummary.refrescate();
			}
		});
		
		tabbedPane.setSelectedIndex(0);
		
		
		final JSplitPane center = 
			new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
					this.taskList, 
					tabbedPane);
		center.setContinuousLayout(true);
		return center;
	}

	public void stop() {
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

}
