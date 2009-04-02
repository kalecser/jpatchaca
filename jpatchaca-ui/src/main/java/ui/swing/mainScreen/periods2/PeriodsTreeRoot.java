package ui.swing.mainScreen.periods2;

import java.util.ArrayList;
import java.util.List;

import periodsInTasks.MonthYear;
import periodsInTasks.PeriodsInTasksHome;

public class PeriodsTreeRoot implements PeriodsTreeItem{


	private final PeriodsInTasksHome periodsInTasksHome;

	public PeriodsTreeRoot(PeriodsInTasksHome periodsInTasksHome){
		this.periodsInTasksHome = periodsInTasksHome;				
	}
	
	@Override
	public String caption() {
		return "root";
	}

	@Override
	public PeriodsTreeItem[] children() {
		
		List<MonthYearTreeItem> children = new ArrayList<MonthYearTreeItem>();
		for (MonthYear monthYear : periodsInTasksHome.monthYears()){
			children.add(new MonthYearTreeItem(monthYear));
		}
		
		return children.toArray(new MonthYearTreeItem[]{});
		
	}

}
