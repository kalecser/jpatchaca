package ui.swing.tray;

import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntervalMenu extends PopupMenu{
	private static final long serialVersionUID = 1L;
	private final boolean past;

	public interface IntervalSelectedListener {

		public void intervalClicked(long millis);
		
	}

	public IntervalMenu(String label, final IntervalSelectedListener listener){
		this(label, listener, false);
	}
	
	public IntervalMenu(String label, final IntervalSelectedListener listener, boolean past){
		super(label);
		this.past = past;
		initialize(listener);
	}

	private void initialize(final IntervalSelectedListener listener) {
		this.add("Now");
		this.addSeparator();
		
		if (past){
			this.add("30 minutes ago");
			this.add("60 minutes ago");
			this.add("90 minutes ago");
		} else {
			this.add("in 30 minutes");
			this.add("in 60 minutes");
			this.add("in 90 minutes");
		}
			
		
		this.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Now")) {
					listener.intervalClicked(0);
				}else if (e.getActionCommand().contains("30 minutes")) {
					listener.intervalClicked(PatchacaTray.HALF_AN_HOUR);
				} else if (e.getActionCommand().contains("60 minutes")) {
					listener.intervalClicked( PatchacaTray.ONE_HOUR);
				} else if (e.getActionCommand().contains("90 minutes")) {
					listener.intervalClicked( PatchacaTray.ONE_HOUR + PatchacaTray.HALF_AN_HOUR);
				} else {
					throw new IllegalArgumentException("Invalid menu action " + e.getActionCommand() );
				}
		
			}
		});
	}
	
	
}
