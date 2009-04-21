package tasks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;

import tasks.delegates.StartTaskData;
import basic.NonEmptyString;

public class StartTaskDataParser {

	public StartTaskData parse(final NonEmptyString taskname) {
		final String minutesAgoregex = "\\s([\\d]+)\\sminutes\\sago";
		final String unbox = taskname.unbox();
		if (unbox.matches(".+" + minutesAgoregex)) {
			final String taskNameWithoutMilliseconds = unbox.replaceFirst(
					minutesAgoregex, "");
			final Integer minutesAgo = getMinutesAgo(minutesAgoregex, unbox);

			return new StartTaskData(new NonEmptyString(
					taskNameWithoutMilliseconds),
					(int) (minutesAgo * DateUtils.MILLIS_PER_MINUTE));
		}

		return new StartTaskData(taskname, 0);
	}

	private Integer getMinutesAgo(final String minutesAgoregex,
			final String unbox) {
		final Pattern minutesAgoPatter = Pattern.compile(minutesAgoregex);
		final Matcher matcher = minutesAgoPatter.matcher(unbox);
		matcher.find();

		final Integer minutesAgo = Integer.valueOf(matcher.group(1));
		return minutesAgo;
	}
}
