package commons.swing;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JAutoCompleteTextFieldTestFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JAutoCompleteTextField comp;

	public JAutoCompleteTextFieldTestFrame(List<? extends Object> elements){
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JAutoCompleteTextFieldModel model = new JAutoCompleteTextFieldModel(elements);
		comp = new JAutoCompleteTextField(20, model);
		panel.add(comp);
		add(panel, BorderLayout.CENTER);
		add(new JButton("ok"), BorderLayout.EAST);
		setLocation(100, 100);
		pack();
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		
		
		
		JAutoCompleteTextFieldTestFrame frame = new JAutoCompleteTextFieldTestFrame(Arrays.asList("foo", "bar", "baz", "baa", "become", "ball", "bounce", "before"));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void dispose(){
		comp.dispose();
	}
}
