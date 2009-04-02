package statistics.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jmock.MockObjectTestCase;

import statistics.SummaryItem;
import statistics.SummaryItemImpl;
import statistics.TaskSummarizer;
import statistics.TaskSummarizerImpl;
import statistics.tests.environment.FakeTask;
import tasks.tasks.TaskView;


public class TaskSummarizerTest extends MockObjectTestCase {

	final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public void testSummarizeTaskPerDay() throws ParseException{

		final TaskView task1 = 
			new FakeTask("task1")
				.withWorkHoursDay(toDate("18/12/2005"), 1.0)
				.withWorkHoursDay(toDate("17/12/2005"), 2.0);
		
		final TaskView task2 = 
			new FakeTask("task2")
				.withWorkHoursDay(toDate("18/12/2005"), 2.0)
				.withWorkHoursDay(toDate("19/12/2005"), 1.0);
				
		
		final TaskSummarizer taskSummarizer = new TaskSummarizerImpl();
		final List<SummaryItem> items = taskSummarizer.summarizePerDay(Arrays.asList(task2,task1));
		
		assertEquals(4, items.size());
		assertEquals(new SummaryItemImpl(toDate("19/12/2005"),"task2",1.0), items.get(0));
		assertEquals(new SummaryItemImpl(toDate("18/12/2005"),"task2",2.0), items.get(1));
		assertEquals(new SummaryItemImpl(toDate("18/12/2005"),"task1",1.0), items.get(2));
		assertEquals(new SummaryItemImpl(toDate("17/12/2005"),"task1",2.0), items.get(3));
	}
	
	public void testSummarizeTaskPerMonth() throws ParseException{

		final TaskView task1 = 
			new FakeTask("task1")
				.withWorkHoursDay(toDate("14/11/2005"), 1.0)
				.withWorkHoursDay(toDate("17/12/2005"), 2.0)
				.withWorkHoursDay(toDate("18/12/2005"), 2.0);
		
		final TaskView task2 = 
			new FakeTask("task2")
				.withWorkHoursDay(toDate("14/11/2005"), 2.0)
				.withWorkHoursDay(toDate("15/11/2005"), 1.0);
		
		final TaskSummarizer taskSummarizer = new TaskSummarizerImpl();
		final List<SummaryItem> items = taskSummarizer.summarizePerMonth(Arrays.asList(task2,task1));
		
		assertEquals(3, items.size());
		assertEquals(new SummaryItemImpl(toDate("01/12/2005"),"task1",4.0), items.get(0));
		assertEquals(new SummaryItemImpl(toDate("01/11/2005"),"task2",3.0), items.get(1));
		assertEquals(new SummaryItemImpl(toDate("01/11/2005"),"task1",1.0), items.get(2));
	}

	private Date toDate(String date) throws ParseException {
		return dateFormat.parse(date);
	}

}
