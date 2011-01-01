package ui.swing.tasks;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import periods.Period;

public class SelectedTaskPeriodsTest {

	@Test
	public void testSelectedTaskPeriods() {
		final SelectedTaskSource selectedTask = new SelectedTaskSource();
		final SelectedTaskPeriods periods = new SelectedTaskPeriodsImpl(
				selectedTask);

		Assert.assertEquals(0, periods.currentSize());

		final ui.swing.tasks.MockTask t = new ui.swing.tasks.MockTask("foo");
		t.addPeriod(new Period(new Date(1)));
		selectedTask.supply(t);
		Assert.assertEquals(1, periods.currentSize());
	}
}
