package periods.adapters;

import periods.PatchacaPeriodsOperator;
import tasks.adapters.ui.operators.MainScreenOperator;

class PatchacaPeriodsOperatorUsingUI implements PatchacaPeriodsOperator {

	private final MainScreenOperator mainScreen;

	PatchacaPeriodsOperatorUsingUI() {
		mainScreen = new MainScreenOperator();
	}

	@Override
	public void editPeriod(final String taskName, final int periodIndex,
			final String start, final String stop) {

		mainScreen.selectTask(taskName);
		mainScreen.editPeriod(periodIndex, start, stop);

	}

	@Override
	public void editPeriod(final String taskName, final int periodIndex,
			final String start) {
		mainScreen.selectTask(taskName);
		mainScreen.editPeriod(periodIndex, start);

	}

	@Override
	public void assertPeriodCount(final String taskName, final int count) {

		mainScreen.assertPeriodsCount(taskName, count);
	}

	@Override
	public void removePeriod(final String taskName, final int i) {
		mainScreen.removePeriod(taskName, i);
	}

	@Override
	public void addPeriod(final String taskName) {
		mainScreen.addPeriod(taskName);
	}

	@Override
	public void assertPeriodDay(final String taskName, final int i,
			final String dateMM_DD_YYYY) {
		mainScreen.assertPeriodDay(taskName, i, dateMM_DD_YYYY);
	}

	@Override
	public void editPeriodDay(final String taskName, final int i,
			final String dateMM_DD_YYYY) {
		mainScreen.editPeriodDay(taskName, i, dateMM_DD_YYYY);
	}

	@Override
	public void removePeriods(final String taskName, final int beginIndex,
			final int endIndex) {
		mainScreen.selectTask(taskName);
		mainScreen.removePeriods(beginIndex, endIndex);

	}

}
