package commons.swing;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class JAutoCompleteAbstractTests {


	protected List<? extends Object> elements;

	@Before
	public void setup(){
		elements = Arrays.asList(
				"foo", 
				"bar",
				"baz",
				"buz",
				"tux",
				"tão",
				"taoo");
		
	}
	
	
	private void assertPossibilitiesFor(String criteria, String[] result){
		List<? extends Object> possibilities = possibilitiesFor(criteria);
		Assert.assertArrayEquals(result, possibilities.toArray());
	}
	
	protected abstract List<? extends Object> possibilitiesFor(String criteria);


	@Test
	public void testSinglePossibility(){
		assertPossibilitiesFor("fo", new String[]{"foo"});
	}

	@Test
	public void testMultiplePossibilities(){
		assertPossibilitiesFor("b",new String[]{"bar", "baz", "buz"});
	}
	
	@Test
	public void testCaseInsensitive(){
		assertPossibilitiesFor("Fo",new String[]{"foo"});
	}
	
	@Test
	public void testNull(){
		assertPossibilitiesFor(null,new String[0]);
	}
	
	@Test
	public void testEmpty(){
		assertPossibilitiesFor("",new String[0]);
	}
	
	@Test
	public void testSpecialCharacters(){
		assertPossibilitiesFor("Ta",new String[]{"tão", "taoo"});
		assertPossibilitiesFor("tã",new String[]{"tão", "taoo"});
	}
	
	@Test
	public void testDifferentLength(){
		assertPossibilitiesFor("Taoo",new String[]{"taoo"});
	}
}
