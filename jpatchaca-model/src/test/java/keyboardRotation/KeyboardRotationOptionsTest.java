package keyboardRotation;

import junit.framework.Assert;

import org.junit.Test;

import tasks.MockEventsSystem;

import events.SetKeyboardRotationOptions;
import events.persistence.MustBeCalledInsideATransaction;

public class KeyboardRotationOptionsTest {

	
	@Test
	public void setKeyboardRotationOptions(){
		boolean supressDialogs = true;
		writeEvent(new SetKeyboardRotationOptions(supressDialogs));
		Assert.assertEquals(true, options.supressShakingDialog());
		
	}

	KeyboardRotationOptions options = new KeyboardRotationOptions();
	private void writeEvent(
			SetKeyboardRotationOptions setKeyboardRotationOptions) {
		try {
			new SetKeyboardRotationOptionsProcessor(options, new MockEventsSystem()).execute(setKeyboardRotationOptions);
		} catch (MustBeCalledInsideATransaction e) {
			//supress
		}
	}
}
