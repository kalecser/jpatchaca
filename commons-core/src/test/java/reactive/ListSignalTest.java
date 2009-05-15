package reactive;

import lang.Maybe;

import org.junit.Assert;
import org.junit.Test;
import org.reactive.Signal;
import org.reactive.Source;

public class ListSignalTest {

	
	
	@Test
	public void testAdd(){
		ListSource<String> list = new ListSource<String>();
		list.add("foo");
		
		Assert.assertEquals(1, list.size().currentValue().intValue());
		Assert.assertEquals("foo", list.currentGet(0).unbox());
	}
	
	
	@Test
	public void testGet(){
		ListSource<String> list = new ListSource<String>();
		
		Source<Integer> index = new Source<Integer>(0);
		Signal<Maybe<String>> value = list.get(index);
		
		Assert.assertEquals(null, value.currentValue());
		list.add("foo");
		Assert.assertEquals("foo", value.currentValue().unbox());
		
		index.supply(1);
		Assert.assertEquals(null, value.currentValue());
		list.add("bar");
		Assert.assertEquals("bar", value.currentValue().unbox());
	}
	
	@Test
	public void testRemove(){
		ListSource<String> list = new ListSource<String>();
		
		Source<Integer> index = new Source<Integer>(0);
		Signal<Maybe<String>> value = list.get(index);
		
		list.add("foo");
		Assert.assertEquals("foo", value.currentValue().unbox());
		list.remove("foo");
		Assert.assertEquals(null, value.currentValue());
		Assert.assertEquals(0, list.currentSize());
	}
}
