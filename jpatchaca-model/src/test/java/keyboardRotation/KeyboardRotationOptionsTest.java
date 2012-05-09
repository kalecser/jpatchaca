package keyboardRotation;

import junit.framework.Assert;

import org.junit.Test;

import tasks.MockEventsSystem;
import events.SetKeyboardRotationOptions2;
import events.persistence.MustBeCalledInsideATransaction;

public class KeyboardRotationOptionsTest {

	
	@Test
	public void setKeyboardRotationOptions(){
		boolean supressDialogs = true;
		boolean remoteSystemIntegration = true;
		writeEvent(new SetKeyboardRotationOptions2(supressDialogs, remoteSystemIntegration));
		Assert.assertEquals(true, options.supressShakingDialog());
		Assert.assertEquals(true, options.isRemoteIntegrationActive());
		
	}

	KeyboardRotationOptions options = new KeyboardRotationOptions();
	private void writeEvent(
			SetKeyboardRotationOptions2 setKeyboardRotationOptions) {
		try {
			new SetKeyboardRotationOptions2Processor(options, new MockEventsSystem()).execute(setKeyboardRotationOptions);
		} catch (MustBeCalledInsideATransaction e) {
			//supress
		}
	}
}
