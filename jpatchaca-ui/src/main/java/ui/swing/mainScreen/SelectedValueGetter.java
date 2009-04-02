package ui.swing.mainScreen;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JList;
import javax.swing.SwingUtilities;

class SelectedValueGetter implements Runnable {

	private final JList list;
	private Object _selectedValue;

	private SelectedValueGetter(final JList list) {
		this.list = list;

	}

	@Override
	public void run() {
		_selectedValue = list.getSelectedValue();
	}

	public Object selectedValue() {
		return _selectedValue;
	}
	
	public static Object getSelectedValueInSwingThread(final JList tasksList) {
		final SelectedValueGetter selectedValue = new SelectedValueGetter(tasksList);
		
		runGetter(selectedValue);
		
		final Object selectedValue2 = selectedValue.selectedValue();
		return selectedValue2;
	}

	private static void runGetter(final SelectedValueGetter selectedValue) {
		if (SwingUtilities.isEventDispatchThread()){
			selectedValue.run();
			return;
		}
		
		try {
			SwingUtilities.invokeAndWait(selectedValue);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		} catch (final InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}