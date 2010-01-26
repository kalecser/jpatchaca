package main;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import model.PatchacaModelContainerFactory;

import org.picocontainer.MutablePicoContainer;

import statistics.ProjectVelocityCalculator;
import statistics.ProjectVelocityCalculatorImpl;
import statistics.TaskSummarizer;
import statistics.TaskSummarizerImpl;
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
import ui.swing.mainScreen.periods.PeriodsTableWhiteboard;
import ui.swing.mainScreen.periods.RemovePeriodsDialogController;
import ui.swing.mainScreen.periods.RemovePeriodsDialogModel;
import ui.swing.mainScreen.tasks.TaskExclusionScreen;
import ui.swing.mainScreen.tasks.TaskScreenController;
import ui.swing.mainScreen.tasks.TaskScreenModelImpl;
import ui.swing.mainScreen.tasks.WindowManager;
import ui.swing.mainScreen.tasks.summary.SummaryScreen;
import ui.swing.options.OptionsScreen;
import ui.swing.options.OptionsScreenModelImpl;
import ui.swing.presenter.Presenter;
import ui.swing.tasks.SelectedTaskPeriods;
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
import basic.durable.HardwareClockImpl;
import basic.mock.MockHardwareClock;

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
		return new PatchacaModelContainerFactory().create(hardwareClock);
	}

	private static void registerSWINGStuff(final MutablePicoContainer container) {
		container.addComponent(UIEventsExecutor.class,
				UIEventsExecutorImpl.class);

		container.addComponent(PatchacaUncaughtExceptionHandler.class,
				PathcacaDefaultExceptionHandler.class);

		container.addComponent(ProjectVelocityCalculator.class,
				ProjectVelocityCalculatorImpl.class);
		container.addComponent(TooltipForTask.class, TooltipForTaskImpl.class);
		container.addComponent(LabelTooltipProvider.class,
				LabelTooltipProviderImpl.class);
		container.addComponent(TaskSummarizer.class, TaskSummarizerImpl.class);
		container.addComponent(basic.Formatter.class, FormatterImpl.class);

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
		container.addComponent(SelectedTaskPeriods.class);
		container.addComponent(SelectedTaskName.class);
		container.addComponent(TaskList.class);
		container.addComponent(TaskListModel.class, TaskListModelImpl.class);
		container.addComponent(StartTaskScreenModelImpl.class);
		container.addComponent(StartTaskScreen.class);
		container.addComponent(StartTaskController.class);
		container.addComponent(TrayIconStartTaskMessage.class);

		container.addComponent(TaskListSystemMediator.class);
		container.addComponent(SummaryScreen.class);
		container.addComponent(PeriodsTableWhiteboard.class);
		container.addComponent(PeriodsTableModel.class);
		container.addComponent(RemovePeriodsDialogController.class);
		container.addComponent(RemovePeriodsDialogModel.class);
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

		makePatchacaTrayStopShowingStatusMessages(container);

		return container;

	}

	private static void makePatchacaTrayStopShowingStatusMessages(
			final MutablePicoContainer container) {
		final PatchacaTray tray = container.getComponent(PatchacaTray.class);
		tray.test_mode = true;
	}

	public static MutablePicoContainer createTransientNonUIContainer(
			final MockHardwareClock mockHardwareClock) {
		final MutablePicoContainer nonUIContainer = createNonUIContainer(mockHardwareClock);
		nonUIContainer.addComponent(new TransientDirectory());
		return nonUIContainer;
	}

}
