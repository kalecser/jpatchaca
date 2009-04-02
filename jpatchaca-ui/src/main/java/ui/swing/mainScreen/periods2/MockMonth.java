package ui.swing.mainScreen.periods2;

public class MockMonth implements PeriodsTreeItem{

	private final String value;
	private final boolean withChild;

	public MockMonth(String value) {
		this(value, false);	
	}
	
	public MockMonth(String value, boolean withChild) {
		this.value = value;
		this.withChild = withChild;
	}
	
	@Override
	public PeriodsTreeItem[] children() {
		if (withChild)
			return new PeriodsTreeItem[]{new MockMonth("child")};
		
		return new PeriodsTreeItem[]{};
	}

	@Override
	public String caption() {
		return value;
	}

	

}
