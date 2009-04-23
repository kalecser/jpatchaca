/*
 * Created on 23/04/2009
 */
package ui.swing.mainScreen;

public class LabelsListModelImpl implements LabelsListModel {

	private final LabelTooltipProvider tooltips;

	public LabelsListModelImpl(final LabelTooltipProvider tooltips) {
		this.tooltips = tooltips;
	}

	public LabelTooltipProvider getTooltips() {
		return tooltips;
	}
}
