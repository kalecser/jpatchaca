package ui.swing.options;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.ComparisonFailure;

public class Assert {
	@SuppressWarnings("unchecked")
	public static <T> void assertThat(T actual, Matcher<T> matcher) {
		if (actual instanceof String)
			assertThat("", (String) actual, (Matcher<String>) matcher);
		else
			org.junit.Assert.assertThat(actual, matcher);
	}
	
	public static void assertThat(String reason, String actual,
			Matcher<String> matcher) {
		if (matcher.matches(actual))
			return;
		final Description description = new StringDescription();
		description.appendDescriptionOf(matcher);
		final String expected = description.toString(); 
		final Description actualDescription = new StringDescription();
		actualDescription.appendValue(actual);
		final String actualDescribed = actualDescription.toString(); 
		final String message = reason;
		String cleanMessage= message == null ? "" : message;
		throw new ComparisonFailure(cleanMessage, (String) expected,
				(String) actualDescribed);
		
	}
}
