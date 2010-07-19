/*
 * Created on 21/04/2009
 */
package ui.swing.mainScreen;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

import statistics.ProjectVelocityCalculator;
import tasks.TaskView;
import tasks.notes.NoteView;
import basic.Formatter;

public final class TooltipForTaskImpl implements TooltipForTask {

	private final Formatter formatter;
	private final ProjectVelocityCalculator projectVelocityCalculator;

	public TooltipForTaskImpl(final Formatter formatter,
			final ProjectVelocityCalculator projectVelocityCalculator) {
		this.formatter = formatter;
		this.projectVelocityCalculator = projectVelocityCalculator;
	}

	@Override
	public String getTooltipForTask(final TaskView item) {
		final String budgetBallance = formatter.formatNumber(item
				.budgetBallanceInHours());
		final String budget = item.budgetInHours() == null ? "-" : formatter
				.formatNumber(item.budgetInHours());
		final String timeSpent = formatter.formatNumber(((double) item
				.totalTimeInMillis())
				/ DateUtils.MILLIS_PER_HOUR);
		String pv = "-";
		String jiraIssue = "None";
		if (item.getJiraIssue() != null) {
			jiraIssue = String.format("[%s] %s", item.getJiraIssue().unbox()
					.getKey(), item.getJiraIssue().unbox().getSummary());
		}

		if (item.budgetInHours() != null) {
			pv = formatter.formatNumber(projectVelocityCalculator
					.calculate(item));
		}

		final StringBuffer tooltip = new StringBuffer();

		tooltip.append("<html>" + "<b>" + item.name() + "</b>" + "<br>Budget: "
				+ budget + "<br>Time Spent: " + timeSpent + "<br>Ballance: "
				+ budgetBallance + "<br>PV: " + pv);

		tooltip.append("<br>Jira Issue: " + jiraIssue);

		tooltip
				.append("<table><tr><td><b>Date</b></td><td><b>Note</b></td></tr>");
		for (final NoteView note : item.notes()) {
			final String date = FastDateFormat.getInstance().format(
					note.timeStamp());
			tooltip.append("<tr><td>" + date + "</td><td>" + note.text()
					+ "</td></tr>");
		}

		tooltip.append("</table></html>");

		return tooltip.toString();
	}
}