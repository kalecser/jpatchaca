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
	void ediTask(String taskName, String taskNewName);
	
	void startTask(String taskName);
	void startTaskHalfAnHourAgo(String taskName);
	void startNewTaskNow(String taskName);
	void stopTask();
	
	void assertActiveTask(String taskName);

	void assertTimeSpent(String taskName, int periodIndex, long timeSpentInMinutes);
	void assertTimeSpentInMinutes(String taskName, long timeSpentInMillis);



	
}