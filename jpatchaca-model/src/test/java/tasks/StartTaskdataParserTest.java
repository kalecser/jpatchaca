package tasks;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import tasks.StartTaskDataParser;
import tasks.delegates.StartTaskData;
import basic.NonEmptyString;

public class StartTaskdataParserTest {

	@Test
	public void testParser() {
		testParse("foo", "foo", 0);
		testParse("foo 60 minutes ago", "foo", (int) DateUtils.MILLIS_PER_HOUR);
		testParse("foo 60", "foo 60", 0);
	}

	private void testParse(final String string, final String taskName,
			final int millisecondsAgo) {
		final StartTaskDataParser parser = new StartTaskDataParser();
		final StartTaskData data = parser.parse(new NonEmptyString(string));

		Assert.assertEquals(data.taskName().unbox(), taskName);
		Assert.assertEquals(data.millisecondsAgo(), (Integer) millisecondsAgo);
	}

}
