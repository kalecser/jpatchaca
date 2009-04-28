package commons.swing;

import java.util.List;

import javax.swing.JLabel;

import org.junit.Assert;
import org.junit.Test;

public final class SwingManipulationDetectorTest {

	@SuppressWarnings("unused")
	// needed for the test
	private void invokeSwingMethod() {
		final JLabel label = new JLabel();
		new Runnable(){
			@Override
			public void run() {
				label.setText("foo");				
			}
		};
	}

	@Test
	public void testSwingManipulationDetector() {
		SwingManipulationChecker detector = new SwingManipulationChecker();
		List<String> detected = detector
				.detect(SwingManipulationDetectorTest.class);

		Assert.assertArrayEquals(
				new String[] { 
						"javax/swing/JLabel <init> ()V", 
						"javax/swing/JLabel setText (Ljava/lang/String;)V" }, 
				detected.toArray());
	}
}
