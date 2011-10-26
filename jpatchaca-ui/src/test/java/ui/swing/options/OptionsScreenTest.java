package ui.swing.options;

import lang.Maybe;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import ui.swing.options.OptionsScreenModel.Data;
import ui.swing.presenter.PresenterImpl;
import ui.swing.presenter.mock.UIEventsExecutorMock;

public class OptionsScreenTest {

	private final Mockery m = new JUnit4Mockery();
	private final OptionsScreenModel modelMock = m.mock(OptionsScreenModel.class);
	private final PresenterImpl presenter = new PresenterImpl(new UIEventsExecutorMock());

	@Test
	public void testOkNow_shouldYieldTheSameData() {
		final Data data = newDataWithAllValuesSet();
		expectScreenToWriteDataEqualToTheDataRead(data);
		openOptionsScreen();
		ok();
		assertExpectationsSatisfied();
	}

	@Ignore
	@Test
	public void testUseOldIcons() {
		openOptionsScreen();
		@SuppressWarnings("unused")
		OptionsScreenOperator op = new OptionsScreenOperator();
	}

	@After
	public void closeAllWindows() {
		presenter.closeAllWindows();
	}

	private Data newDataWithAllValuesSet() {
		final Data data = new Data();
		data.issueStatusManagementEnabled = true;
		data.jiraUrl = Maybe.wrap("http://jpatchaca.org/jira");
		data.jiraUserName = Maybe.wrap("foo");
		data.jiraPassword = Maybe.wrap("bar");
		data.supressShakingDialog = true;
		return data;
	}

	private void expectScreenToWriteDataEqualToTheDataRead(final Data data) {
		m.checking(new Expectations() {
			{
				oneOf(modelMock).readDataFromSystem();
				will(returnValue(data));
				oneOf(modelMock).writeDataIntoSystem(with(equal(data)));
			}
		});
	}

	private void openOptionsScreen() {
		new OptionsScreenPresenter(modelMock, presenter).show();
	}

	private void ok() {
		new OptionsScreenOperator().ok();
	}

	private void assertExpectationsSatisfied() {
		m.assertIsSatisfied();
	}
}
