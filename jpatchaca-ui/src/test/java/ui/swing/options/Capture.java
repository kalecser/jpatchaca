package ui.swing.options;

import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

public class Capture<T> implements Action {
    public T get() {
        return value;
    }

    private T value;

	@Override
	public void describeTo(Description description) {
		description.appendText("capture");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(Invocation invocation) throws Throwable {
        value = (T) invocation.getParameter(0);
		return null;
	}
}
