package statistics;

import java.util.List;

import tasks.tasks.TaskView;

public interface ProjectVelocityCalculator {

	public abstract Double calculate(List<TaskView> tasks);

	public abstract Double calculate(TaskView item);

}