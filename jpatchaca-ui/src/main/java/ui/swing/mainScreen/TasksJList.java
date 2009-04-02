/**
 * 
 */
package ui.swing.mainScreen;

import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListModel;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

import statistics.ProjectVelocityCalculator;
import tasks.tasks.NoteView;
import tasks.tasks.TaskView;
import basic.Formatter;
import basic.FormatterImpl;

class TasksJList extends JList {
	private static final long serialVersionUID = 1L;
	
	private final ProjectVelocityCalculator projectVelocityCalculator;

	public TasksJList(ListModel listModel, ProjectVelocityCalculator projectVelocityCalculator) {
		super(listModel);
		this.projectVelocityCalculator = projectVelocityCalculator;
	}
	
	@Override
	public String getToolTipText(MouseEvent event) {
		 final int index = locationToIndex(event.getPoint());
		 final TaskView item = (TaskView) getModel().getElementAt(index);
		 
		 final String toolTip = getTooltipForTask(item);
		 
		 return toolTip;			 
	}

	private String getTooltipForTask(TaskView item) {
		final Formatter formatter = new FormatterImpl();
		 
		 final String budgetBallance = formatter.formatNumber(item.budgetBallanceInHours());
		 final String budget = item.budgetInHours() == null ? "-" : formatter.formatNumber(item.budgetInHours());
		 final String timeSpent = formatter.formatNumber( ((double) item.totalTimeInMillis()) / DateUtils.MILLIS_PER_HOUR);
		 String pv = "-";
		 if (item.budgetInHours() != null ) {
			 pv = formatter.formatNumber(projectVelocityCalculator.calculate(item));
		 }
		 
		 final StringBuffer tooltip = new StringBuffer();
		 
		 tooltip.append( "<html>" +
		 					"<b>" + item.name() + "</b>" +
		 					"<br>Budget: " + budget + 
		 					"<br>Time Spent: " + timeSpent +
		 					"<br>Ballance: " + budgetBallance +
		 					"<br>PV: " + pv);
		 
		 tooltip.append("<table><tr><td><b>Date</b></td><td><b>Note</b></td></tr>");
		 for (final NoteView note : item.notes()){
			 final String date = FastDateFormat.getInstance().format(note.timeStamp());
			 tooltip.append("<tr><td>" + date + "</td><td>" + note.text() + "</td></tr>");	 
		 }
		 
		 tooltip.append("</table></html>");
		 
		return tooltip.toString();
	}

}