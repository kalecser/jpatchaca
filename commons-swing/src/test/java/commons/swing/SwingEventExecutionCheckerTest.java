package commons.swing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JLabel;

import org.junit.Assert;
import org.junit.Test;

public class SwingEventExecutionCheckerTest {

	@SuppressWarnings("unused")
	// needed for the test
	private void registerListener() {
		final JLabel label = new JLabel();
		label.addFocusListener(new FocusListener() {
		
			@Override
			public void focusLost(FocusEvent e) {
				bar();
				new JLabel().setText("foo");
				new MockSwingEventExecutor().focusLost();
			}
		
			private void bar() {
				
			}

			@Override
			public void focusGained(FocusEvent e) {
				SwingEventExecutionCheckerTest.this.testMethod();
				SwingEventExecutionCheckerTest.this.testPrivateMethod();
			}
		});
	}

	protected void testPrivateMethod() {
		
	}

	public void testMethod() {
		
	}

	@Test
	public void testSwingManipulationDetector() {
		SwingEventExecutionChecker checker = new SwingEventExecutionChecker();
		List<String> errors = checker
				.check(SwingEventExecutionCheckerTest.class);

		Assert.assertArrayEquals(
				new String[] { 
						"commons/swing/SwingEventExecutionCheckerTest testMethod ()V",
						"commons/swing/SwingEventExecutionCheckerTest testPrivateMethod ()V"}, 
				errors.toArray());
	}
	
}
