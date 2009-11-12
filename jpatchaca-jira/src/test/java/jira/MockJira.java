package jira;

public class MockJira implements Jira {

	@Override
	public String[] getIssues(String name, String password, String address) {
		
		if (name.equals("foo") && password.equals("bar") && address.equals("http://baz")){
			return new String[]{"foo32 - I", "bar44 - H"};
		}
		
		return null;
		
	}

}
