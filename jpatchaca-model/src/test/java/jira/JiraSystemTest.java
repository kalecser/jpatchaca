package jira;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import periods.Period;
import tasks.persistence.MockEventsConsumer;
import tasks.tasks.Tasks;

public class JiraSystemTest {

	

	@Test
	public void testAddWorklog(){
		
		Period period = new Period(date(0l), date(DateUtils.MILLIS_PER_HOUR));
		addWorklogFor(period);
		Assert.assertEquals("1h 0m", timeLogged());
		
	}
	
	@Test
	public void testAddWorklogWithOverride(){
		
		Period period = new Period(date(0l), date(DateUtils.MILLIS_PER_HOUR));
		overrideWorkLog(period, "2h 25m");
		addWorklogFor(period);
		Assert.assertEquals("2h 25m", timeLogged());
		
	}
	
	@Test
	public void testAddWorklogOverrideWrongFormat(){
		
		Period period = new Period(date(0l), date(DateUtils.MILLIS_PER_HOUR));
		overrideWorkLog(period, "2:25");
		addWorklogFor(period);
		Assert.assertEquals("1h 0m", timeLogged());
		
	}

	private final JiraMock jira = new JiraMock();
	private final JiraWorklogOverride  jiraWorklogOverride = new JiraWorklogOverride(); 
	private final JiraSystemImpl jiraSystem = new JiraSystemImpl(jira, new MockEventsConsumer(), new Tasks(), jiraWorklogOverride);

	private void overrideWorkLog(Period period, String timeSpent) {
		jiraWorklogOverride.overrideTimeSpentForPeriod(timeSpent, period);
		
	}
	
	private String timeLogged() {
		return jira.timeLoggedFor("key");
	}

	private void addWorklogFor(Period period) {
		jiraSystem.logWorkOnIssue(period, "key");
	}

	private Date date(long millis) {
		return new Date(millis);
	}
}
