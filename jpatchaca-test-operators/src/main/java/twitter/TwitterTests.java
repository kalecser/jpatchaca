package twitter;

import org.junit.Assert;
import org.junit.Test;

import tasks.PatchacaTasksOperator;

public abstract class TwitterTests {

	protected abstract TwitterOperator getTwitterOperator();
	protected abstract PatchacaTasksOperator getTasksOperator();
	
	@Test
	public void testTwitterConfig(){
		Assert.assertEquals("", getTwitterOperator().twitterUserName());
		Assert.assertEquals("", getTwitterOperator().twitterPassword());
		Assert.assertEquals(false, getTwitterOperator().twitterEnabled());
		getTwitterOperator().enableTwitterLogging("foo", "bar");
		Assert.assertEquals("foo", getTwitterOperator().twitterUserName());
		Assert.assertEquals("bar", getTwitterOperator().twitterPassword());
		Assert.assertEquals(true, getTwitterOperator().twitterEnabled());
	}
	
}
