package ui.swing.mainScreen.periods2;

import periodsInTasks.MonthYear;

public class MonthYearTreeItem implements PeriodsTreeItem{


	private final MonthYear monthYear;

	public MonthYearTreeItem(final MonthYear monthYear) {
		this.monthYear = monthYear;
	}

	@Override
	public String caption() {
		return monthYear.print();
	}

	@Override
	public PeriodsTreeItem[] children() {
		return new PeriodsTreeItem[]{};
	}

}
