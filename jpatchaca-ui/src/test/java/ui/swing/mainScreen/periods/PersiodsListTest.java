package ui.swing.mainScreen.periods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import lang.Maybe;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.UnhandledException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.reactive.Signal;

import periods.Period;
import periodsInTasks.PeriodsInTasksSystem;
import tasks.TasksSystem;
import tasks.tasks.Tasks;
import ui.swing.mainScreen.periods.mock.PeriodsListOperator;
import ui.swing.presenter.ActionPane;
import ui.swing.presenter.PatchacaPrintToConsoleExceptionHandler;
import ui.swing.presenter.Presenter;
import ui.swing.presenter.UIAction;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.utils.UIEventsExecutor;
import ui.swing.utils.UIEventsExecutorImpl;
import basic.Formatter;
import basic.FormatterImpl;
import basic.HardwareClock;

public class PersiodsListTest {



	@Test
	@Ignore
	public void testMergePeriods(){
		assertMergePeriodsButtonWithRestriction();
		
		addPeriod("08:00 to 09:00");
		addPeriod("10:00 to 11:00");
		
		selectPeriods("08:00 to 09:00", "10:00 to 11:00");
		assertMergePeriodsButtonWithRestriction();
		
		addPeriod("11:00 to 12:00");
		selectPeriods("10:00 to 11:00", "11:00 to 12:00");
		assertMergePeriodsButtonWithRestriction();
		
		selectPeriods("10:00 to 11:00", "11:00 to 12:00");
		assertMergePeriodsButtonReadyToGo();
		
		assertPeriodsBackgroundGreen("10:00 to 11:00", "11:00 to 12:00");
		assertPeriodsBackgroundWhite("08:00 to 09:00");
	}
	
	private PeriodsList subject;
	private Presenter presenter;
	private PeriodsListOperator operator;
	private SelectedTaskPeriodsMock selectedTaskperiods;
	
	private void assertPeriodsBackgroundWhite(String... periods) {
		throw new NotImplementedException();
	}


	private void assertPeriodsBackgroundGreen(String... periods) {
		throw new NotImplementedException();		
	}

	private void assertMergePeriodsButtonReadyToGo() {
		operator.assertMergeperiodsButtonWithRegularBorder();		
	}


	private void selectPeriods(String... periods) {
		
		List<Integer> rowsToselect = new ArrayList<Integer>();
		for (String period : periods){
			rowsToselect.add(getPeriodIndex(parsePeriod(period)));
		}
		
		operator.selectRows(rowsToselect);
		
	}


	private Integer getPeriodIndex(Period parsePeriod) {
		int periodsCount = selectedTaskperiods.currentSize();
		for (int i = 0; i < periodsCount; i++) {
			Signal<Maybe<Period>> currentPeriod = selectedTaskperiods.get(i);
			
			if (currentPeriod.currentValue().unbox().equals(parsePeriod)){
				return periodsCount - i;
			}
			
		}
		
		return -1;
	}


	private void addPeriod(String period) {
		selectedTaskperiods.addPeriod(parsePeriod(period));
	}


	private Period parsePeriod(String periodAsString) {
		
		String start = periodAsString.split("\\s")[0].trim();
		String end = periodAsString.split("\\s")[2].trim();
		
		SimpleDateFormat format = new SimpleDateFormat("hh:mm");
		
		try {
			return new Period(format.parse(start), format.parse(end));
		} catch (ParseException e) {
			throw new UnhandledException(e);
		}
	}


	private void assertMergePeriodsButtonWithRestriction() {
		operator.assertMergeperiodsButtonWithEmptyBorder();		
	}


	@Before
	public void setup(){
		SelectedTaskSource selectedTaskSource = new SelectedTaskSource();
		PeriodsTableWhiteboard periodsWhiteboard = new PeriodsTableWhiteboard();
		selectedTaskperiods = new SelectedTaskPeriodsMock();
		PeriodsInTasksSystem periodsInTasks = null;
		TasksSystem tasksSystem = null;
		PeriodsInTasksSystem periodsSystem = null;
		Formatter formatter = new FormatterImpl();
		HardwareClock machineClock = null;
		RemovePeriodsDialogController removePeriodsDialogController = null;
		Tasks tasks = null;
		UIEventsExecutor executor = new UIEventsExecutorImpl(new PatchacaPrintToConsoleExceptionHandler());
		presenter = new Presenter(executor);
		
		PeriodsTableModel periodsTableModel = new PeriodsTableModel(
				tasksSystem, 
				periodsSystem, 
				formatter, 
				selectedTaskSource, 
				selectedTaskperiods, 
				periodsWhiteboard, 
				executor);
		PeriodsTable periodsTable = new PeriodsTable(periodsTableModel);
		
		
		subject = new PeriodsList(
				selectedTaskSource, 
				periodsInTasks, 
				periodsTable, 
				machineClock, 
				removePeriodsDialogController, 
				periodsWhiteboard, 
				tasks);
		
		showFrame();
		operator = new PeriodsListOperator();
	}
	
	
	@After
	public void tearDown(){
		presenter.stop();
	}


	private void showFrame() {
		presenter.showOkCancelDialog(new ActionPane() {
			
			@Override
			public JPanel getPanel() {
				return subject;
			}
			
			@Override
			public UIAction action() {
				return null;
			}
		}, "test periods list");
	}
}
