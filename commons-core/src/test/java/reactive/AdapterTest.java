package reactive;

import org.junit.Assert;
import org.junit.Test;
import org.reactive.Source;

public class AdapterTest {

	
	@Test
	public void testAdapter(){
	
		Source<String> name = new Source<String>("foo");
		Adapter<String, Integer> charCount = 
		new Adapter<String, Integer>(name, 
				new Functor<String, Integer>(){
					@Override
					public Integer evaluate(String value) {
						return value.length();
					}
					
				}
		);
		
		Assert.assertEquals(charCount.output().currentValue(), (Integer)3);
		
		name.supply("foobar");
		Assert.assertEquals(charCount.output().currentValue(), (Integer)6);
		
	}
	
	
}
