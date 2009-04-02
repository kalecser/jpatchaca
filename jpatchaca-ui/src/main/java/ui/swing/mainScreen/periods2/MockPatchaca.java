package ui.swing.mainScreen.periods2;

public class MockPatchaca implements PeriodsTreeItem{

	@Override
	public PeriodsTreeItem[] children() {
		return new PeriodsTreeItem[]{new MockMonth("10/2008"), new MockMonth("09/2008", true)};
	}

	@Override
	public String caption() {
		return "Root";
	}



}
