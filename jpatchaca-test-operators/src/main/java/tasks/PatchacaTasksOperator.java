/**
 * 
 */
package tasks;


public interface PatchacaTasksOperator{

	void setTime(int time);
	void advanceTimeBy(long millis);

	String allLabelName();
	boolean isTaskInLabel(String taskName, String labelnName);
	void createTask(String taskName);
	void createTaskAndAssignToLabel(String taskName, String labelName);
	void createTaskWithJiraIntegration(String taskName, String jiraKey);

	void ediTask(String taskName, String taskNewName);
	void ediTaskJiraKey(String string, String string2);
	
	void startTask(String taskName);
	void startTaskHalfAnHourAgo(String taskName);
	void startNewTaskNow(String taskName);
	void stopTask();
	
	void assertActiveTask(String taskName);

	void assertTimeSpent(String taskName, int periodIndex, long timeSpentInMinutes);
	void assertTimeSpentInMinutes(String taskName, long timeSpentInMillis);
	void assertJiraKeyForTask(String jiraKey, String taskName);



	
}