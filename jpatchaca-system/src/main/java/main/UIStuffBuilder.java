package main;

import jira.JiraIssueStatusManagement;

import org.picocontainer.MutablePicoContainer;

import statistics.ProjectVelocityCalculator;
import statistics.ProjectVelocityCalculatorImpl;
import statistics.TaskSummarizer;
import statistics.TaskSummarizerImpl;
import ui.commandLine.CommandLineInterfaceImpl;
import ui.commandLine.PatchacaSocketServer;
import ui.swing.errorLog.ErrorLogScreen;
import ui.swing.events.EventsListPane;
import ui.swing.events.EventsListPaneModel;
import ui.swing.events.EventsListPanePresenter;
import ui.swing.jira.JiraIssueStatusActionPane;
import ui.swing.mainScreen.JiraBrowserIntegrationImpl;
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
import ui.swing.mainScreen.StatusBar;
import ui.swing.mainScreen.TaskContextMenu;
import ui.swing.mainScreen.TaskContextMenuSystemMediator;
import ui.swing.mainScreen.TaskList;
import ui.swing.mainScreen.TaskListModel;
import ui.swing.mainScreen.TaskListModelImpl;
import ui.swing.mainScreen.TaskListSystemMediator;
import ui.swing.mainScreen.TooltipForTask;
import ui.swing.mainScreen.TooltipForTaskImpl;
import ui.swing.mainScreen.TopBarModelImpl;
import ui.swing.mainScreen.WorkLoggerUsingSwing;
import ui.swing.mainScreen.newAndNoteworthy.NewAndNoteworthyMenu;
import ui.swing.mainScreen.newAndNoteworthy.NewAndNoteworthyModelImpl;
import ui.swing.mainScreen.newAndNoteworthy.NewAndNoteworthyPresenter;
import ui.swing.mainScreen.periods.PeriodsList;
import ui.swing.mainScreen.periods.PeriodsTable;
import ui.swing.mainScreen.periods.PeriodsTableModel;
import ui.swing.mainScreen.periods.PeriodsTableWhiteboard;
import ui.swing.mainScreen.periods.RemovePeriodsDialogController;
import ui.swing.mainScreen.periods.RemovePeriodsDialogModel;
import ui.swing.mainScreen.tasks.TaskExclusionScreen;
import ui.swing.mainScreen.tasks.TaskScreenController;
import ui.swing.mainScreen.tasks.TaskScreenModelImpl;
import ui.swing.mainScreen.tasks.WindowManager;
import ui.swing.mainScreen.tasks.day.WorklogListModel;
import ui.swing.mainScreen.tasks.day.DayTasksList;
import ui.swing.mainScreen.tasks.day.DayTasksTable;
import ui.swing.mainScreen.tasks.day.DayTasksTableModel;
import ui.swing.mainScreen.tasks.day.WorklogTopPanel;
import ui.swing.mainScreen.tasks.summary.SummaryHoursFormat;
import ui.swing.mainScreen.tasks.summary.SummaryScreen;
import ui.swing.mainScreen.tasks.summary.SummaryTableModel;
import ui.swing.options.OptionsScreenModelImpl;
import ui.swing.options.OptionsScreenPresenter;
import ui.swing.presenter.PresenterImpl;
import ui.swing.singleInstance.ShowMainScreenOnSecondRun;
import ui.swing.tasks.SelectedTaskPeriodsImpl;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.tasks.StartTaskPresenter;
import ui.swing.tasks.StartTaskScreen;
import ui.swing.tasks.StartTaskScreenModelImpl;
import ui.swing.tray.PatchacaTray;
import ui.swing.tray.PatchacaTrayModelImpl;
import ui.swing.tray.PatchacaTrayTasksFacadeMediator;
import ui.swing.tray.TrayIconStartTaskMessage;
import ui.swing.users.SwingTasksUserImpl;
import ui.swing.users.SwinglabelsUser;
import ui.swing.utils.PatchacaUncaughtExceptionHandler;
import ui.swing.utils.UIEventsExecutor;
import ui.swing.utils.UIEventsExecutorImpl;
import ui.swing.utils.Whiteboard;
import wheel.io.ui.impl.DeferredDirectoryBoundPersistence;
import wheel.io.ui.impl.JFrameBoundsKeeperImpl;
import basic.FormatterImpl;

final class UIStuffBuilder {

	static void registerUIStuff(final MutablePicoContainer container) {
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

		container.addComponent(PresenterImpl.class);

		container.addComponent(TopBarModelImpl.class);
		container.addComponent(NewAndNoteworthyModelImpl.class);
		container.addComponent(NewAndNoteworthyMenu.class);
		container.addComponent(NewAndNoteworthyPresenter.class);
		container
				.addComponent(MainScreenModel.class, MainScreenModelImpl.class);
		container.addComponent(MainScreen.class, MainScreenImpl.class);
		container.addComponent(MainScreenSetup.class);

		container.addComponent(ShowMainScreenOnSecondRun.class);

		container.addComponent(OptionsScreenPresenter.class);
		container.addComponent(OptionsScreenModelImpl.class);
		container.addComponent(ErrorLogScreen.class);
		container.addComponent(SwinglabelsUser.class);
		container.addComponent(SelectedTaskSource.class);
		container.addComponent(SelectedTaskPeriodsImpl.class);
		container.addComponent(SelectedTaskName.class);
		container.addComponent(TaskList.class);
		container.addComponent(TaskListModel.class, TaskListModelImpl.class);
		container.addComponent(StartTaskScreenModelImpl.class);
		container.addComponent(StartTaskScreen.class);
		container.addComponent(StartTaskPresenter.class);
		container.addComponent(TrayIconStartTaskMessage.class);

		container.addComponent(TaskListSystemMediator.class);
		container.addComponent(SummaryHoursFormat.class);
		container.addComponent(SummaryTableModel.class);
		container.addComponent(SummaryScreen.class);
		container.addComponent(PeriodsTableWhiteboard.class);
		container.addComponent(PeriodsTableModel.class);
		container.addComponent(PeriodsTable.class);
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
		
		container.addComponent(WorklogTopPanel.class);
		container.addComponent(DayTasksTable.class);
		container.addComponent(DayTasksTableModel.class);
		container.addComponent(WorklogListModel.class);
		container.addComponent(DayTasksList.class);		

		container.addComponent(CommandLineInterfaceImpl.class);
		container.addComponent(WorkLoggerUsingSwing.class);
		container.addComponent(PatchacaSocketServer.class);

		container.addComponent(JiraIssueStatusManagement.class);
		container.addComponent(StatusBar.class);
		container.addComponent(JiraIssueStatusActionPane.class);
		container.addComponent(JiraBrowserIntegrationImpl.class);
	}

}