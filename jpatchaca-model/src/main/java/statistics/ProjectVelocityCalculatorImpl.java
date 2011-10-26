package statistics;

import java.util.ArrayList;
import java.util.List;

import tasks.TaskView;

@SuppressWarnings("boxing")
public class ProjectVelocityCalculatorImpl implements ProjectVelocityCalculator {
	
	@Override
	public Double calculate(List<TaskView> tasks) {
		
		double totalTimeSpent = 0.0;
		double totalTimeInBudget = 0.0;
		
		for(final TaskView task : tasks)
			if (task.budgetInHours() != null) {
				totalTimeInBudget += task.budgetInHours();
				totalTimeSpent += task.totalTimeInHours();
			}
		
		return totalTimeSpent / totalTimeInBudget;
	}

	@Override
	public Double calculate(TaskView item) {
		final List<TaskView> tasks = new ArrayList<TaskView>();
		tasks.add(item);
		return calculate(tasks);
	}
}
