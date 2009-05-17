/*
 * Created on 23/04/2009
 */
package ui.swing.mainScreen;

import java.util.List;

import labels.LabelsSystem;
import statistics.ProjectVelocityCalculator;
import tasks.TaskView;
import basic.Formatter;

public class LabelTooltipProviderImpl implements LabelTooltipProvider {

	private final LabelsSystem labelsSystem;
	private final ProjectVelocityCalculator calculator;
	private final Formatter formatter;

	public LabelTooltipProviderImpl(final LabelsSystem labelsSystem,
			final Formatter formatter,
			final ProjectVelocityCalculator calculator) {
		this.labelsSystem = labelsSystem;
		this.formatter = formatter;
		this.calculator = calculator;
	}

	@Override
	public String getTipFor(final String item) {
		final List<TaskView> tasks = labelsSystem.tasksInlabel(item);
		final String numberOfTasks = formatter.formatNumber((double) tasks
				.size());
		final Double pv = calculator.calculate(tasks);

		String pvRepresentation = "-";
		if (!pv.equals(Double.NaN)) {
			pvRepresentation = formatter.formatNumber(pv);
		}

		final String toolTip = "<html>" + "<b>" + item + "</b>"
				+ "<br>There are " + numberOfTasks + " tasks in the label"
				+ "<br>PV: " + pvRepresentation + "</html>";

		return toolTip;
	}

}
