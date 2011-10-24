package commons.swing;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class FloatingArea extends JWindow{
	
	private static final long serialVersionUID = 1L;
	
	private final JFrame subject;
	private final ComponentAdapter floatingAreaComponentListener;

	private JPanel contentsOrNull;
	
	public FloatingArea(JFrame owner) throws HeadlessException {		
		super();
		if (owner == null)
			throw new IllegalArgumentException("owner must not be null");
		
		this.subject = owner;
		floatingAreaComponentListener = new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent e) {
				hideFloatingAreaAndClearContents();
			}
		};
		
		setAlwaysOnTop(false);
		
		setLayout(new BorderLayout());
		
		autoCompleteWindowFollowAncestor();
	}

	private void autoCompleteWindowFollowAncestor() {
		
		subject.addComponentListener(new ComponentAdapter(){

			@Override
			public void componentMoved(ComponentEvent e) {
				adjustWindowToSubject();
			}

			@Override
			public void componentResized(ComponentEvent e) {
				adjustWindowToSubject();
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				hideFloatingArea();
			}
			
			@Override
			public void componentShown(ComponentEvent e) {
				showFloatingArea();
			}
		});
		
		
		subject.addWindowListener(new WindowAdapter(){
			@Override
			public void windowDeiconified(WindowEvent e) {
				showFloatingArea();
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				hideFloatingArea();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				showFloatingArea();
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				hideFloatingArea();
			}
			
			
		});
	}

	protected void adjustWindowToSubject() {
		Point p = new Point();
		SwingUtilities.convertPointToScreen(p, subject);
		p.setLocation(p.getX() + subject.getWidth() - this.getWidth() - subject.getInsets().right, p.getY() + subject.getInsets().top);
		this.setLocation(p);
		
	}

	void showFloatingArea() {
		
		if (contentsOrNull == null)
			return;
		
			setVisible(true);
			pack();
	}

	void hideFloatingArea() {
		setVisible(false);
	}
	
	public void setContents(JPanel contents){
		
		if (contentsOrNull != null){
			this.remove(contentsOrNull);
		}
		
		if (contents == null){
			pack();
			return;
		}
		
		
		contents.addComponentListener(floatingAreaComponentListener);
			
		this.add(contents, BorderLayout.CENTER);
		pack();
		adjustWindowToSubject();
		contentsOrNull = contents;		
		FloatingArea.this.setVisible(true);
	}

	void hideFloatingAreaAndClearContents() {
		hideFloatingArea();
		contentsOrNull = null;
	}

}
