package tasks.delegates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;

import ui.swing.mainScreen.Delegate;
import basic.NonEmptyString;

public class StartTaskByNameDelegate extends Delegate<StartTaskData>{

	public void starTask(NonEmptyString taskname){
		
		String minutesAgoregex = "\\s([\\d]+)\\sminutes\\sago";
		String unbox = taskname.unbox();
		if (unbox.matches(".+" + minutesAgoregex)){
			String taskNameWithoutMilliseconds = unbox.replaceFirst(minutesAgoregex, "");
			Integer minutesAgo = getMinutesAgo(minutesAgoregex, unbox);
			
			super.execute(new StartTaskData(new NonEmptyString(taskNameWithoutMilliseconds), (int)(minutesAgo * DateUtils.MILLIS_PER_MINUTE)));
			return;
		}
			
		
		super.execute(new StartTaskData(taskname, 0));
	}

	private Integer getMinutesAgo(String minutesAgoregex, String unbox) {
		Pattern minutesAgoPatter = Pattern.compile(minutesAgoregex);
		Matcher matcher = minutesAgoPatter.matcher(unbox);
		matcher.find();
		
		Integer minutesAgo = Integer.valueOf(matcher.group(1));
		return minutesAgo;
	}
	
}
