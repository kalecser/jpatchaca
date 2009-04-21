package wheel.swing;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import javax.swing.JTextField;

import org.reactive.Receiver;
import org.reactive.Signal;


public class TextFieldBinder {

	public final static WeakHashMap<JTextField, Receiver<String>> bindings = new WeakHashMap<JTextField, Receiver<String>>();
	
	public static void bind(JTextField textField,
			Signal<String> signal) {
		
		final WeakReference<JTextField> weakTextField = new WeakReference<JTextField>(textField);
		Receiver<String> receiver = new Receiver<String>() {	
			@Override
			public void receive(String pulse) {
				JTextField textField = weakTextField.get();
				if (textField != null)
					textField.setText(pulse);
			}
		};
		
		bindings.put(textField, receiver);
		signal.addReceiver(receiver);
	}

}
