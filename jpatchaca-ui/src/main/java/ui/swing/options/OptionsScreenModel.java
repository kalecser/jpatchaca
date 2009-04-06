package ui.swing.options;

import org.reactivebricks.pulses.Signal;

public interface OptionsScreenModel {

	public abstract void setConfig(boolean selected, String username,
			String password);

	public abstract Signal<Boolean> twitterEnabled();

	public abstract Signal<String> userName();

	public abstract Signal<String> password();

}