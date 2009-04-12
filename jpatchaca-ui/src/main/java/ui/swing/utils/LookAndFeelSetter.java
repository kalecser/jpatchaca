/*
 * Created on 12/04/2009
 */
package ui.swing.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.lang.SystemUtils;
import org.picocontainer.Startable;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.windows.WindowsLookAndFeel;

public class LookAndFeelSetter implements Startable {

	@Override
	public void start() {
		final LookAndFeel laf = SystemUtils.IS_OS_WINDOWS ? new WindowsLookAndFeel()
				: new PlasticXPLookAndFeel();
		try {
			UIManager.setLookAndFeel(laf);
		} catch (final UnsupportedLookAndFeelException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					e.getMessage(), e);
		}
	}

	@Override
	public void stop() {
		// Nothing special
	}

}
