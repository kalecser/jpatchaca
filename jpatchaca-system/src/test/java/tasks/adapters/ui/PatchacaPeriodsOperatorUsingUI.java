package tasks.adapters.ui;

import periods.PatchacaPeriodsOperator;

class PatchacaPeriodsOperatorUsingUI implements PatchacaPeriodsOperator {


	private MainScreenOperator mainScreen;

	PatchacaPeriodsOperatorUsingUI(){
		mainScreen = new MainScreenOperator();	
	}
	
	@Override
	public void editPeriod(final String taskName, final int periodIndex, final String start,
			final String stop) {
		
		mainScreen.selectTask(taskName);
		mainScreen.editPeriod(periodIndex, start, stop);		
		
	}

	@Override
	public void editPeriod(String taskName, int periodIndex, String start) {
		mainScreen.selectTask(taskName);
		mainScreen.editPeriod(periodIndex, start);
		
	}

	@Override
	public void assertPeriodCount(String taskName, int count) {
		
		mainScreen.assertPeriodsCount(taskName, count);
	}

	@Override
	public void removePeriod(String taskName, int i) {
		mainScreen.removePeriod(taskName, i);		
	}

	@Override
	public void addPeriod(String taskName) {
		mainScreen.addPeriod(taskName);
	}

	@Override
	public void assertPeriodDay(String taskName, int i, String dateMM_DD_YYYY) {
		mainScreen.assertPeriodDay(taskName, i, dateMM_DD_YYYY );		
	}

	@Override
	public void editPeriodDay(String taskName, int i, String dateMM_DD_YYYY) {
		
		mainScreen.editPeriodDay(taskName, i, dateMM_DD_YYYY );
	}

}
