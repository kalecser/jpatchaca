package wheel.swing;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import javax.swing.JCheckBox;

import org.reactivebricks.pulses.Pulse;
import org.reactivebricks.pulses.Receiver;
import org.reactivebricks.pulses.Signal;


public class CheckboxSignalBinder {

	public static WeakHashMap<JCheckBox, Receiver<Boolean>> bindings = new WeakHashMap<JCheckBox, Receiver<Boolean>>();
	
	public static void bind(JCheckBox checkbox,
			Signal<Boolean> signal) {
		
		final WeakReference<JCheckBox> weakCheckbox = new WeakReference<JCheckBox>(checkbox);
		Receiver<Boolean> receiver = new Receiver<Boolean>() {	
			@Override
			public void receive(Pulse<Boolean> pulse) {
				JCheckBox mycheckbox = weakCheckbox.get();
				if (mycheckbox != null)
					mycheckbox.setSelected(pulse.value());
			}
		};
		
		bindings.put(checkbox, receiver);
		signal.addReceiver(receiver);
	}

}
