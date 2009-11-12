package jira;

public class MockJira implements Jira {

	@Override
	public String[] getIssues(String name, String password, String address) throws JiraException {
		
		if (name.equals("existingUser") && password.equals("bar") && address.equals("http://baz")){
			return new String[]{"foo32 - I", "bar44 - H"};
		}
		
		throw new JiraException("error");
		
	}

}
