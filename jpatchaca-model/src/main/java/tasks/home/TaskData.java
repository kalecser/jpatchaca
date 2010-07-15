package tasks.home;

import basic.NonEmptyString;



public class TaskData {

	private final String taskName;
	private final Double budget;
	private final String label;

	public TaskData(NonEmptyString taskName, Double budget, String label) {
		this.taskName = taskName.unbox();
		this.budget = budget;
		this.label = label;
	}

	public final String getTaskName() {
		return taskName;
	}

	public Double getBudget() {
		return budget;
	}
	
	public String getLabel(){
		return label;
	}
}
