package statistics.tests;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import static org.junit.Assert.*;

import statistics.ProjectVelocityCalculator;
import statistics.ProjectVelocityCalculatorImpl;
import tasks.TaskView;

@SuppressWarnings("boxing")
public class ProjectVelocityCalculatorTest {
	
	private final Mockery context = new JUnit4Mockery();
	final ProjectVelocityCalculator calculator = new ProjectVelocityCalculatorImpl();
	
	@Test
	public void testCalculationForASingleTask() {
		final TaskView task = context.mock(TaskView.class);
		
		
		context.checking(new MyExpectations() {

			{
				setTaskBudgetAndTotalTime(task, 100.0, 110.0);
			}
		});
		final double expected = 110.0/100.0;
		final double actual = calculator.calculate(task).doubleValue();
		assertEquals(expected, actual, 0);
	}
	
	@Test
	public void testCalculationForACollectionOfTasks() {
		final TaskView task1 = context.mock(TaskView.class, "1");		
		final TaskView task2 = context.mock(TaskView.class, "2");		
		final TaskView task3 = context.mock(TaskView.class, "3");

		final List<TaskView> tasks = new ArrayList<TaskView>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
				
		context.checking(new MyExpectations() {

			{
				setTaskBudgetAndTotalTime(task1, 100.0, 110.0);
				setTaskBudgetAndTotalTime(task2, 50.0, 70.0);	
				setTaskBudgetAndTotalTime(task3, null, null);	
			}
		});
		final double expected = (110.0 + 70)/ (100.0 + 50.0);
		final double actual = calculator.calculate(tasks).doubleValue();
		assertEquals( expected, actual, 0);
	}

	static class MyExpectations extends Expectations {
	
		void setTaskBudgetAndTotalTime(TaskView taskMocker1, Double budgetInHours, Double totalTaskTime) {
			atLeast(1).of(taskMocker1).budgetInHours(); will(returnValue(budgetInHours));		
		
			if (totalTaskTime != null) {
				oneOf(taskMocker1).totalTimeInHours(); will(returnValue(totalTaskTime));
			}
		}
	
	}
}
