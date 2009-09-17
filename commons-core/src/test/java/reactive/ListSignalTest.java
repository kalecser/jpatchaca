package reactive;

import lang.Maybe;

import org.junit.Assert;
import org.junit.Test;
import org.reactive.Signal;

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
		
		Signal<Maybe<String>> value = list.get(0);
		
		Assert.assertEquals(null, value.currentValue());
		list.add("foo");
		Assert.assertEquals("foo", value.currentValue().unbox());
		
		Signal<Maybe<String>> secondRow = list.get(1);
		Assert.assertEquals(secondRow.currentValue(), null);
		list.add("bar");
		Assert.assertEquals("bar", secondRow.currentValue().unbox());
	}
	
	@Test
	public void testRemove(){
		ListSource<String> list = new ListSource<String>();
		
		Signal<Maybe<String>> value = list.get(0);
		
		list.add("foo");
		list.add("bar");
		Assert.assertEquals("foo", value.currentValue().unbox());
		list.remove("foo");
		Assert.assertEquals("bar", value.currentValue().unbox());
		Assert.assertEquals(1, list.currentSize());
	}
}
