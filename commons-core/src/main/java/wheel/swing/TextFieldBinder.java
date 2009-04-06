package wheel.swing;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import javax.swing.JTextField;

import org.reactivebricks.pulses.Pulse;
import org.reactivebricks.pulses.Receiver;
import org.reactivebricks.pulses.Signal;


public class TextFieldBinder {

	public static WeakHashMap<JTextField, Receiver<String>> bindings = new WeakHashMap<JTextField, Receiver<String>>();
	
	public static void bind(JTextField textField,
			Signal<String> signal) {
		
		final WeakReference<JTextField> weakTextField = new WeakReference<JTextField>(textField);
		Receiver<String> receiver = new Receiver<String>() {	
			@Override
			public void receive(Pulse<String> pulse) {
				JTextField textField = weakTextField.get();
				if (textField != null)
					textField.setText(pulse.value());
			}
		};
		
		bindings.put(textField, receiver);
		signal.addReceiver(receiver);
	}

}
