package jira;

import java.util.HashMap;
import java.util.Map;

import periods.Period;

public class JiraWorklogOverride {

	private static final String TIME_SPENT_PATTERN = "[0-9]*h [0-9]*m";
	Map<Period, String> overrides = new HashMap<Period, String>();
	
	public void overrideTimeSpentForPeriod(String timeSpent, Period period) {
		String trimmedTimeSpent = timeSpent.trim();
		if (trimmedTimeSpent.matches(TIME_SPENT_PATTERN)){
			overrides.put(period, trimmedTimeSpent);					
		}
	}

	public String getDuration(Period period) {
		
		if (overrides.containsKey(period)){
			String overriden = overrides.get(period);
			return overriden;
		}
		
		final String duration = JiraUtil.humanFormat(period.totalTime()).trim();
		return duration;
	}

}
