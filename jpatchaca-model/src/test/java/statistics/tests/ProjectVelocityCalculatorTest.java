package statistics.tests;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import statistics.ProjectVelocityCalculator;
import statistics.ProjectVelocityCalculatorImpl;
import tasks.tasks.TaskView;


public class ProjectVelocityCalculatorTest extends MockObjectTestCase {
	
	public void testCalculationForASingleTask() {
		final Mock taskMocker = mock(TaskView.class);
		
		final TaskView task = (TaskView) taskMocker.proxy();		
		final ProjectVelocityCalculator calculator = new ProjectVelocityCalculatorImpl();
		
		setTaskBudgetAndTotalTime(taskMocker, 100.0, 110.0);
		assertEquals(110.0/100.0, calculator.calculate(task));
	}
	
	public void testCalculationForACollectionOfTasks() {
		final Mock taskMocker1 = mock(TaskView.class);
		final Mock taskMocker2 = mock(TaskView.class);
		final Mock taskMocker3 = mock(TaskView.class);
		
		final TaskView task1 = (TaskView) taskMocker1.proxy();		
		final TaskView task2 = (TaskView) taskMocker2.proxy();		
		final TaskView task3 = (TaskView) taskMocker3.proxy();

		final List<TaskView> tasks = new ArrayList<TaskView>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		
		final ProjectVelocityCalculator calculator = new ProjectVelocityCalculatorImpl();
		
		setTaskBudgetAndTotalTime(taskMocker1, 100.0, 110.0);
		setTaskBudgetAndTotalTime(taskMocker2, 50.0, 70.0);	
		setTaskBudgetAndTotalTime(taskMocker3, null, null);	
		assertEquals( (110.0 + 70)/ (100.0 + 50.0), calculator.calculate(tasks));
	}

	private void setTaskBudgetAndTotalTime(Mock taskMocker1, Double budgetInHours, Double totalTaskTime) {
		taskMocker1.expects(atLeastOnce()).method("budgetInHours")
			.will(returnValue(budgetInHours));		
		
		if (totalTaskTime != null)
			taskMocker1.expects(once()).method("totalTimeInHours")
				.will(returnValue(totalTaskTime));
	}
}
