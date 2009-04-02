package ui.swing.mainScreen.tasks;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tasks.tasks.TaskView;
import ui.swing.utils.SwingUtils;
import basic.Formatter;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;


public class TaskScreen extends JDialog{

	private static final long serialVersionUID = 1L;
	public static final String TITLE = "Task edition";
	
	private JCheckBox budgetCheckBox;
	private JTextField budgetHours;
	private JButton okButton;
	private JButton cancelButton;
	private boolean okPressed;
	private JTextField taskNameTextBox;
	private final Formatter formatter;
	

	public TaskScreen(Formatter formatter, Window window){
		super(window);
		
		if (window == null)
			throw new IllegalArgumentException();
			
		initialize();
		okPressed(false);
		this.formatter = formatter;
	}
	
	public void setVisible(boolean visible, TaskView taskView) {
		
		if (visible) {
			okPressed(false);
			taskNameTextBox.requestFocus();			
		}
				
		this.taskNameTextBox.requestFocus();
		taskNameTextBox.selectAll();
	
		
		if (taskView == null){
			super.setVisible(visible);
			return;
		}
		
		this.taskNameTextBox.setText(taskView.name());
		if (taskView.budgetInHours() != null) {
			this.budgetCheckBox.setSelected(true);
			this.budgetHours.setText(this.formatter.formatNumber(taskView.budgetInHours()));
		} else {
			this.budgetCheckBox.setSelected(false);
			this.budgetHours.setText("");
		}
		
		super.setVisible(visible);
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible)
			throw new UnsupportedOperationException("Use setVisible(boolean visible, TaskData defaultData) instead.)");
		
		super.setVisible(visible);
	}

	private void initialize() {
		
		
		final JPanel fieldsPanel = buildFieldsPanel();
		final JPanel buttonBar = buildOkCancelBar();
		
		this.setLayout(new BorderLayout());
		this.add(fieldsPanel, BorderLayout.CENTER);
		this.add(buttonBar, BorderLayout.SOUTH);
		
		
		this.setPreferredSize(new Dimension(380,170));
		
		SwingUtils.makeLocationrelativeToParent(this, getOwner());
		
		this.setResizable(false);
		this.setTitle(TITLE);		
		this.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		this.setModal(true);
		
		enableCancelOnEsc();
		
		this.pack();
		
	}


	private void enableCancelOnEsc() {
		this.getRootPane().registerKeyboardAction(new ActionListener(){@Override
		public void actionPerformed(ActionEvent e) {
			doCancel();			
		}}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	private JPanel buildOkCancelBar() {
		
		initializeOkButton();
		initializeCancelButton();
		
		final JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(this.okButton, this.cancelButton);
		buttonBar.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		return buttonBar;
	}

	private JPanel buildFieldsPanel() {
		
		this.taskNameTextBox = new JTextField(20);
		initializeBudgetCheckbox();		
		initializeBudgetHours();
		
		final FormLayout layout = new FormLayout("pref, 3dlu, left:pref");
		final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.append("Task name", this.taskNameTextBox);
		builder.nextLine();		
		builder.append(this.budgetCheckBox);				
		builder.append(this.budgetHours);
		final JPanel fieldsPanel = builder.getPanel();
		fieldsPanel.setBorder(BorderFactory.createEmptyBorder(20,15,15,15));

		return fieldsPanel;
	}

	private void initializeCancelButton() {
		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				doCancel();
			}		
		});
	}

	private void initializeOkButton() {
		this.okButton = new JButton("Ok");
		this.okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				okPressed(true);
				setVisible(false);
			}			
		});
		
		getRootPane().setDefaultButton(okButton);
		
	}

	private void initializeBudgetHours() {
		this.budgetHours = new JTextField(5);
		this.budgetHours.setEnabled(false);
	}

	private void initializeBudgetCheckbox() {
		this.budgetCheckBox = new JCheckBox("Budget");
		this.budgetCheckBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				TaskScreen.this.budgetHours.setEnabled(TaskScreen.this.budgetCheckBox.isSelected());				
			}			
		});
	}
	
	public boolean okPressed() {
		return this.okPressed;
	}
	
	private void okPressed(boolean newValue) {
		this.okPressed = newValue;
	}

	public String taskName() {
		return this.taskNameTextBox.getText();
	}

	public Double taskBudget() {
		if (this.budgetCheckBox.isSelected()) {
			try {
				return new DecimalFormat().parse(this.budgetHours.getText()).doubleValue();
			} catch (final ParseException e) {}
		}

		return null;
	}

	private void doCancel() {
		okPressed(false);
		setVisible(false);
	}
	

	
	
	
}
