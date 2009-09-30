package reactive;

import lang.Maybe;

import org.junit.Assert;
import org.junit.Test;
import org.reactive.Signal;

public class ListRedirectorTest {

	
	@Test
	public void testRedirectList(){
		ListRedirector<String> redirector = new ListRedirector<String>();
		Assert.assertEquals(0, redirector.currentSize());
		
		ListSource<String> subjectA = new ListSource<String>();
		subjectA.add("foo");
		redirector.redirect(subjectA);
		Assert.assertEquals(1, redirector.currentSize());

		Signal<Maybe<String>> firstElement = redirector.get(0); 
		Assert.assertEquals("foo", firstElement.currentValue().unbox());
		
		ListSource<String> subjectB = new ListSource<String>();
		subjectB.add("bar");
		subjectB.add("baz");
		redirector.redirect(subjectB);
		Assert.assertEquals(2, redirector.currentSize());
		Assert.assertEquals("bar", firstElement.currentValue().unbox());
		
	}
	
}
