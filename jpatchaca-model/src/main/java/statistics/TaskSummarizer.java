package statistics;

import java.util.List;

import tasks.tasks.TaskView;

public interface TaskSummarizer {

	List<SummaryItem> summarizePerDay(List<TaskView> tasks);
	List<SummaryItem> summarizePerMonth(List<TaskView> tasks);

}
