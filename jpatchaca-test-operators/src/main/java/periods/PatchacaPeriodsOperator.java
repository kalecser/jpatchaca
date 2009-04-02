package periods;

public interface PatchacaPeriodsOperator {

	void addPeriod(String taskName);
	void editPeriod(String taskName, int periodIndex, String start, String stop);
	void editPeriod(String taskName, int i, String start);
	void removePeriod(String taskName, int i);
	void assertPeriodCount(String taskName, int count);
	
	void editPeriodDay(String taskName, int i, String dateMM_DD_YYYY);
	void assertPeriodDay(String taskName, int i, String dateMM_DD_YYYY);


}
