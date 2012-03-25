package statistics;

import java.util.List;

import tasks.TaskView;

public interface TaskSummarizer {

	List<SummaryItem> summarizePerDay(List<TaskView> tasks);
	List<SummaryItem> summarizePerMonth(List<TaskView> tasks);
	List<SummaryItem> summarizePerWeek(List<TaskView> tasks);

}
