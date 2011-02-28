package jira;

import java.util.HashMap;
import java.util.Map;

import periods.Period;

public class JiraWorklogOverride {

	Map<Period, String> overrides = new HashMap<Period, String>();
	
	public void overrideTimeSpentForPeriod(String timeSpent, Period period) {
		
		if (timeSpent.matches("[0-9]*h [0-9]*m")){
			overrides.put(period, timeSpent);					
		}
		
	}

	public String getDuration(Period period) {
		
		if (overrides.containsKey(period)){
			String overriden = overrides.get(period);
			return overriden;
		}
		
		final String duration = JiraUtil.humanFormat(period.totalTime());
		return duration;
	}

}
