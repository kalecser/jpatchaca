package periods;

import org.junit.Ignore;

import junit.framework.TestCase;
import tasks.PatchacaTasksOperator;

public abstract class PeriodsTest extends TestCase{

	protected abstract PatchacaTasksOperator createTasksOperator();
	protected abstract PatchacaPeriodsOperator createPeriodsOperator();

	private PatchacaTasksOperator tasksOperator;
	private PatchacaPeriodsOperator periodsOperator;
	
	private static final String taskName = "test task";

	@Override
	protected void setUp() throws Exception {
		tasksOperator = createTasksOperator();
		periodsOperator = createPeriodsOperator();
		
		createTask(taskName);
	}

	
	private void editPeriodOrCry(String startTime, String stopTime, int periodIndex, String taskName, int timeSpentInMinutes) {
		periodsOperator.editPeriod(taskName, periodIndex, startTime, stopTime);
		tasksOperator.assertTimeSpent(taskName, periodIndex, timeSpentInMinutes );
	}
	
	
	private void testPeriodsEdition(String startTime, String stopTime, int timeSpentInMinutes) {
		periodsOperator.addPeriod(taskName);
		int periodIndex = 0;
		editPeriodOrCry(startTime, stopTime, periodIndex, taskName, timeSpentInMinutes);
	}
	
	private void createTask(String taskName) {
		tasksOperator.createTask(taskName);
	}

	public void testEditPeriods(){
		testPeriodsEdition("10:00 am", "2:00 pm", 4 * 60);
		
		periodsOperator.addPeriod(taskName);
	}
	
	public void testEditPeriodDay(){
		periodsOperator.addPeriod(taskName);
		periodsOperator.editPeriodDay(taskName, 0, "02_28_2008");
		periodsOperator.assertPeriodDay(taskName, 0 , "02_28_2008");		
	}
	
	public void testEditPeriodDayPreservingStartAndEntTimes(){
		int oneHour = 60;
		
		periodsOperator.addPeriod(taskName);
		periodsOperator.editPeriod(taskName, 0, "02:00 am", "03:00 am");
		periodsOperator.editPeriodDay(taskName, 0, "02_28_2008");
		
		periodsOperator.assertPeriodDay(taskName, 0 , "02_28_2008");
		tasksOperator.assertTimeSpent(taskName, 0, oneHour);
	}
	
	public void testEditPeriodsEndingAfterMidnight(){
		testPeriodsEdition("10:00 am", "5:00 am", 19 * 60);
	}
	
	public void testEditPeriodsWithPreviousPeriods(){
		periodsOperator.addPeriod(taskName);
		periodsOperator.addPeriod(taskName);
		int periodIndex = 1;
		editPeriodOrCry("10:00 am", "2:00 PM", periodIndex, taskName, 4 * 60);
	}
	
	public void testPeriodsRemoval(){
		periodsOperator.addPeriod(taskName);
		periodsOperator.removePeriod(taskName, 0);
		periodsOperator.assertPeriodCount(taskName, 0);
		
	}
	
	@Ignore
	public void testPeriodsIntervalRemoval(){
		periodsOperator.addPeriod(taskName);
		periodsOperator.addPeriod(taskName);
		periodsOperator.removePeriods(taskName, 0, 1);
		periodsOperator.assertPeriodCount(taskName, 0);
		
	}
	
	public void testActivePeriodEditionMightStopTask(){
		tasksOperator.startTask(taskName);
		periodsOperator.editPeriod(taskName, 0, "02:00 pm");
		
		tasksOperator.assertActiveTask(taskName);
		
		periodsOperator.editPeriod(taskName, 0, "02:00 pm", "06:00 pm");
		tasksOperator.assertActiveTask(null);
		tasksOperator.assertTimeSpent(taskName, 0, 4 * 60);
	}

}
