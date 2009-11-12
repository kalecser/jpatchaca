package jira;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import reactive.ListSignal;

public class JiraIssuesRetrieverTest {

	
	private JiraIssuesRetrieverImpl retriever;
	private JiraConfig config;

	@Before
	public void setup() {
		config = new JiraConfig();
		retriever = new JiraIssuesRetrieverImpl(config, new MockJira());
		
		config.supplyUserName("existingUser");
		config.supplyPassword("bar");
		config.supplyJiraAddress("http://baz");
	}
	
	@Test
	public void testConfigVerification(){

		Assert.assertEquals(true, retriever.isConfigured().currentValue());
		
		config.supplyUserName("nonExistingUser");
		Assert.assertEquals(false, retriever.isConfigured().currentValue());
		
		config.supplyUserName("existingUser");
		Assert.assertEquals(true, retriever.isConfigured().currentValue());
		
		config.supplyJiraAddress(null);
		Assert.assertEquals(false, retriever.isConfigured().currentValue());
	}
	
	@Test
	public void testGetIssues(){
		
		ListSignal<String> issues = retriever.issues();
		Assert.assertEquals("foo32 - I", issues.currentGet(0).unbox());
		Assert.assertEquals("bar44 - H", issues.currentGet(1).unbox());
		
		config.supplyUserName("nonExistingUser");
		Assert.assertEquals(0, issues.currentSize());
	}
	
	@Test
	public void testConfigurationError(){
		
				
		config.supplyUserName("nonExistingUser");
		Assert.assertEquals("error",retriever.errorMessage().currentValue());
		
		config.supplyUserName("existingUser");
		Assert.assertEquals("",retriever.errorMessage().currentValue());
	}
	
	
}
