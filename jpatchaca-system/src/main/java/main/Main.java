package main;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import labels.LabelsSystem;
import labels.LabelsSystemImpl;
import localization.BrazilDaylightSavingTimezoneAdjuster;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import periods.impl.PeriodsFactoryImpl;
import periodsInTasks.impl.PeriodsInTasksSystemImpl;
import statistics.ProjectVelocityCalculator;
import statistics.ProjectVelocityCalculatorImpl;
import statistics.TaskSummarizer;
import statistics.TaskSummarizerImpl;
import tasks.ActiveTask;
import tasks.TasksSystemImpl;
import tasks.delegates.CreateTaskDelegate;
import tasks.delegates.StartTaskDataParser;
import tasks.delegates.StartTaskDelegate;
import tasks.home.TasksHomeImpl;
import tasks.persistence.CreateAndStartTaskRegister;
import tasks.persistence.CreateTaskPersistence;
import tasks.persistence.CreateTaskProcessorRegister;
import tasks.persistence.StartTaskPersistence;
import tasks.persistence.StartTaskProcessor2Register;
import tasks.persistence.StartTaskProcessorRegister;
import tasks.processors.CreateAndStartTaskProcessor;
import tasks.processors.CreateTaskProcessor;
import tasks.processors.CreateTaskProcessor2;
import tasks.processors.CreateTaskProcessor2Register;
import tasks.processors.StartTaskProcessor;
import tasks.processors.StartTaskProcessor2;
import tasks.taskName.ActiveTaskName;
import tasks.taskName.TaskNameFactory;
import tasks.tasks.Tasks;
import twitter.TwitterLogger;
import twitter.TwitterOptions;
import twitter.processors.SetTwitterConfigProcessor;
import ui.swing.events.EventsListPane;
import ui.swing.events.EventsListPaneModel;
import ui.swing.events.EventsListPanePresenter;
import ui.swing.mainScreen.LabelTooltipProvider;
import ui.swing.mainScreen.LabelTooltipProviderImpl;
import ui.swing.mainScreen.LabelsList;
import ui.swing.mainScreen.LabelsListModel;
import ui.swing.mainScreen.LabelsListModelImpl;
import ui.swing.mainScreen.LabelsListSystemMediator;
import ui.swing.mainScreen.MainScreen;
import ui.swing.mainScreen.MainScreenImpl;
import ui.swing.mainScreen.MainScreenModel;
import ui.swing.mainScreen.MainScreenModelImpl;
import ui.swing.mainScreen.SelectedTaskName;
import ui.swing.mainScreen.TaskContextMenu;
import ui.swing.mainScreen.TaskContextMenuSystemMediator;
import ui.swing.mainScreen.TaskList;
import ui.swing.mainScreen.TaskListModel;
import ui.swing.mainScreen.TaskListModelImpl;
import ui.swing.mainScreen.TaskListSystemMediator;
import ui.swing.mainScreen.TooltipForTask;
import ui.swing.mainScreen.TooltipForTaskImpl;
import ui.swing.mainScreen.TopBarModel;
import ui.swing.mainScreen.periods.PeriodsList;
import ui.swing.mainScreen.periods.PeriodsTableModel;
import ui.swing.mainScreen.tasks.TaskExclusionScreen;
import ui.swing.mainScreen.tasks.TaskScreenController;
import ui.swing.mainScreen.tasks.TaskScreenModelImpl;
import ui.swing.mainScreen.tasks.WindowManager;
import ui.swing.mainScreen.tasks.summary.SummaryScreen;
import ui.swing.options.OptionsScreen;
import ui.swing.options.OptionsScreenModelImpl;
import ui.swing.presenter.Presenter;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.tasks.StartTaskController;
import ui.swing.tasks.StartTaskScreen;
import ui.swing.tasks.StartTaskScreenModelImpl;
import ui.swing.tray.PatchacaTray;
import ui.swing.tray.PatchacaTrayModelImpl;
import ui.swing.tray.PatchacaTrayTasksFacadeMediator;
import ui.swing.tray.TrayIconStartTaskMessage;
import ui.swing.users.SwingTasksUserImpl;
import ui.swing.users.SwinglabelsUser;
import ui.swing.utils.LookAndFeelSetter;
import ui.swing.utils.PatchacaUncaughtExceptionHandler;
import ui.swing.utils.UIEventsExecutor;
import ui.swing.utils.UIEventsExecutorImpl;
import ui.swing.utils.Whiteboard;
import wheel.io.files.impl.tranzient.TransientDirectory;
import wheel.io.ui.impl.DeferredDirectoryBoundPersistence;
import wheel.io.ui.impl.JFrameBoundsKeeperImpl;
import basic.DeferredExecutor;
import basic.FormatterImpl;
import basic.HardwareClock;
import basic.PatchacaDirectory;
import basic.SystemClockImpl;
import basic.durable.DoubleIdProvider;
import basic.durable.HardwareClockImpl;
import basic.mock.MockHardwareClock;
import events.EventsSystemImpl;
import events.eventslist.CensorFromFile;
import events.eventslist.EventListImpl;
import events.persistence.FileAppenderPersistence;

public class Main {

	private static MutablePicoContainer container;

	public static void main(final String[] args)
			throws UnsupportedLookAndFeelException, IOException {

		keepWorkingOnMinimize();
		setLookAndFeel();

		final MutablePicoContainer container = createDurableSWINGContainer();
		container.start();
	}

	private static void setLookAndFeel() {
		new LookAndFeelSetter().start();
	}

	private static void keepWorkingOnMinimize() {
		System.setProperty("sun.awt.keepWorkingSetOnMinimize", "true");
	}

	private static MutablePicoContainer createSwingContainer(
			final HardwareClock clock) {
		final MutablePicoContainer container = createNonUIContainer(clock);
		final MutablePicoContainer swingContainer = container
				.makeChildContainer();

		registerSWINGStuff(swingContainer);
		return container;
	}

	private static MutablePicoContainer createNonUIContainer(
			final HardwareClock hardwareClock) {
		final MutablePicoContainer container = new PicoBuilder()
				.withConstructorInjection().withLifecycle().withCaching()
				.build();

		// FIXIT Move to registerSWINGStuff? This is UI, as it may display a
		// JOptionPane.
		container.addComponent(PatchacaUncaughtExceptionHandler.class,
				PathcacaDefaultExceptionHandler.class);

		container.addComponent(BrazilDaylightSavingTimezoneAdjuster.class);
		container.addComponent(hardwareClock);
		container.addComponent(DoubleIdProvider.class);
		container.addComponent(PatchacaDirectory.class);
		container.addComponent(SystemClockImpl.class);

		container.addComponent(PeriodsFactoryImpl.class);
		container.addComponent(FileAppenderPersistence.class);
		container.addComponent(CensorFromFile.class);
		container.addComponent(EventListImpl.class);
		container.addComponent(EventsSystemImpl.class);
		container.addComponent(ActiveTask.class);
		container.addComponent(ActiveTaskName.class);
		container.addComponent(TaskNameFactory.class);
		container.addComponent(CreateTaskProcessor2.class);
		container.addComponent(CreateTaskProcessor2Register.class);
		container.addComponent(CreateAndStartTaskRegister.class);
		container.addComponent(CreateAndStartTaskProcessor.class);
		container.addComponent(CreateTaskProcessorRegister.class);
		container.addComponent(CreateTaskProcessor.class);
		container.addComponent(StartTaskProcessorRegister.class);
		container.addComponent(StartTaskProcessor.class);
		container.addComponent(StartTaskDataParser.class);
		container.addComponent(Tasks.class);
		container.addComponent(TasksHomeImpl.class);
		container.addComponent(TasksSystemImpl.class);
		container.addComponent(StartTaskDelegate.class);
		container.addComponent(CreateTaskDelegate.class);
		container.addComponent(StartTaskProcessor2Register.class);
		container.addComponent(StartTaskProcessor2.class);
		container.addComponent(StartTaskPersistence.class);
		container.addComponent(CreateTaskPersistence.class);

		container.addComponent(TwitterOptions.class);
		container.addComponent(TwitterLogger.class);
		container.addComponent(SetTwitterConfigProcessor.class);

		container.addComponent(PeriodsInTasksSystemImpl.class);
		container.addComponent(LabelsSystem.class, LabelsSystemImpl.class);
		registerModelStuff(container);

		return container;
	}

	private static void registerSWINGStuff(final MutablePicoContainer container) {
		container.addComponent(UIEventsExecutor.class,
				UIEventsExecutorImpl.class);
		container.addComponent(DeferredDirectoryBoundPersistence.class);
		container.addComponent(JFrameBoundsKeeperImpl.class);

		container.addComponent(WindowManager.class);
		container.addComponent(Whiteboard.class);

		container.addComponent(SwingTasksUserImpl.class);

		container.addComponent(PatchacaTray.class);
		container.addComponent(PatchacaTrayModelImpl.class);
		container.addComponent(PatchacaTrayTasksFacadeMediator.class);

		container.addComponent(EventsListPaneModel.class);
		container.addComponent(EventsListPane.class);
		container.addComponent(EventsListPanePresenter.class);

		container.addComponent(Presenter.class);

		container.addComponent(TopBarModel.class);
		container
				.addComponent(MainScreenModel.class, MainScreenModelImpl.class);
		container.addComponent(MainScreen.class, MainScreenImpl.class);
		container.addComponent(MainScreenSetup.class);

		container.addComponent(OptionsScreen.class);
		container.addComponent(OptionsScreenModelImpl.class);
		container.addComponent(SwinglabelsUser.class);
		container.addComponent(SelectedTaskSource.class);
		container.addComponent(SelectedTaskName.class);
		container.addComponent(TaskList.class);
		container.addComponent(TaskListModel.class, TaskListModelImpl.class);
		container.addComponent(StartTaskScreenModelImpl.class);
		container.addComponent(StartTaskScreen.class);
		container.addComponent(StartTaskController.class);
		container.addComponent(TrayIconStartTaskMessage.class);

		container.addComponent(TaskListSystemMediator.class);
		container.addComponent(SummaryScreen.class);
		container.addComponent(PeriodsTableModel.class);
		container.addComponent(PeriodsList.class);
		container.addComponent(TaskScreenController.class);
		container.addComponent(TaskScreenModelImpl.class);
		container.addComponent(TaskContextMenu.class);
		container.addComponent(TaskContextMenuSystemMediator.class);
		container.addComponent(LabelsList.class);
		container
				.addComponent(LabelsListModel.class, LabelsListModelImpl.class);
		container.addComponent(LabelsListSystemMediator.class);
		container.addComponent(TaskExclusionScreen.class);

	}

	private static void registerModelStuff(final MutablePicoContainer container2) {
		container2.addComponent(ProjectVelocityCalculator.class,
				ProjectVelocityCalculatorImpl.class);
		container2.addComponent(TooltipForTask.class, TooltipForTaskImpl.class);
		container2.addComponent(LabelTooltipProvider.class,
				LabelTooltipProviderImpl.class);
		container2.addComponent(TaskSummarizer.class, TaskSummarizerImpl.class);
		container2.addComponent(basic.Formatter.class, FormatterImpl.class);
	}

	public static MutablePicoContainer createDurableSWINGContainer() {
		if (container == null) {
			container = createSwingContainer(new HardwareClockImpl());
		}
		return container;
	}

	public static MutablePicoContainer createTransientNonUIContainer() {
		final MutablePicoContainer nonUIContainer = createNonUIContainer(new HardwareClockImpl());
		nonUIContainer.addComponent(new TransientDirectory());
		return nonUIContainer;
	}

	public static MutablePicoContainer createSWINGContainerForTests(
			final HardwareClock hardwareClock) {

		DeferredExecutor.makeSynchronous();
		final MutablePicoContainer container = createNonUIContainer(hardwareClock);
		container.removeComponent(PatchacaDirectory.class);
		container.addComponent(new TransientDirectory());
		registerSWINGStuff(container);

		return container;

	}

	public static MutablePicoContainer createTransientNonUIContainer(
			final MockHardwareClock mockHardwareClock) {
		final MutablePicoContainer nonUIContainer = createNonUIContainer(mockHardwareClock);
		nonUIContainer.addComponent(new TransientDirectory());
		return nonUIContainer;
	}

}
