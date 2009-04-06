package ui.swing.mainScreen.tasks;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

import org.apache.commons.lang.StringUtils;
import org.reactivebricks.commons.lang.Maybe;

import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import basic.Formatter;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;


public class TaskScreen{

	private static final long serialVersionUID = 1L;
	public static final String TITLE = "Task edition";
	
	private final Formatter formatter;
	private final TaskScreenModel model;
	

	public TaskScreen(Formatter formatter, TaskScreenModel model){			
		this.model = model;
		this.formatter = formatter;
	}
	
	private void show() {
		new Thread(){
			@Override
			public void run() {
				new TaskScreenDialog().internalShow(null, null);
			}
		}.start();
		
	}
	
	private void show(final Long time) {
		new Thread() {
			@Override
			public void run() {
				new TaskScreenDialog().internalShow(null, Maybe.wrap(time));
			}
		}.start();
	}
	
	private void show(final Maybe<TaskView> maybeTaskView) {
		new Thread(){
			@Override
			public void run() {
				new TaskScreenDialog().internalShow(maybeTaskView, null);
			}
		}.start();
		
	}

	public void createTaskStarted(long time) {
		show(time);
	}	
	
	public void editSelectedTask() {
		TaskView selectedTask = model.selectedTask();
		if (selectedTask == null)
			show((Maybe<TaskView>)null);
		else
			show(Maybe.wrap(selectedTask));
	}

	public void createTask() {
		show();
	}
	
	class TaskScreenDialog{
		private JTextField taskNameTextBox;
		private JCheckBox budgetCheckBox;
		private JTextField budgetHours;
		private JButton cancelButton;
		private JButton okButton;
		private TaskView taskView;

		public TaskScreenDialog(){
			
		}
		
		public void internalShow(Maybe<TaskView> maybeTaskView, Maybe<Long> time) {
			JDialog dialog = createDialog(time);
			
			taskNameTextBox.requestFocus();			
					
			taskNameTextBox.requestFocus();
			taskNameTextBox.selectAll();
		
			
			
			if (maybeTaskView == null){
				dialog.setVisible(true);
				return;
			}
			
			taskView = maybeTaskView.unbox();
			taskNameTextBox.setText(taskView.name());
			if (taskView.budgetInHours() != null) {
				budgetCheckBox.setSelected(true);
				budgetHours.setText(formatter.formatNumber(taskView.budgetInHours()));
			} else {
				budgetCheckBox.setSelected(false);
				budgetHours.setText("");
			}
						
			dialog.setVisible(true);
		}
		
		private JPanel buildFieldsPanel() {
			
			taskNameTextBox = new JTextField(20);
			budgetCheckBox = createBudgetCheckbox();		
			initializeBudgetHours();
			
			final FormLayout layout = new FormLayout("pref, 3dlu, left:pref");
			final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
			builder.append("Task name", taskNameTextBox);
			builder.nextLine();		
			builder.append(budgetCheckBox);				
			builder.append(budgetHours);
			final JPanel fieldsPanel = builder.getPanel();
			fieldsPanel.setBorder(BorderFactory.createEmptyBorder(20,15,15,15));

			return fieldsPanel;
		}

		private void initializeCancelButton(final JDialog dialog) {
			this.cancelButton = new JButton("Cancel");
			this.cancelButton.addActionListener(new ActionListener() {		
				public void actionPerformed(ActionEvent e) {
					doCancel(dialog);
				}		
			});
		}
		
		private void enableCancelOnEsc(final JDialog dialog) {
			dialog.getRootPane().registerKeyboardAction(new ActionListener(){@Override
			public void actionPerformed(ActionEvent e) {
				doCancel(dialog);			
			}}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		}

		private JPanel buildOkCancelBar(JDialog dialog, Maybe<Long> time) {
			
			initializeOkButton(dialog, time);
			initializeCancelButton(dialog);
			
			final JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(this.okButton, this.cancelButton);
			buttonBar.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			return buttonBar;
		}

		

		private void initializeOkButton(final JDialog dialog, final Maybe<Long> time) {
			this.okButton = new JButton("Ok");
			this.okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
					TaskData data = new TaskData(taskNameTextBox.getText(), getBudget());
					
					if (taskView != null)
						model.editTask(taskView, data);
					else if (time == null)
						model.createTask(data);
					else
						model.createTaskAndStart(data, time.unbox());
					
					dialog.setVisible(false);
				}

				private Double getBudget() {
					String budgetHoursText = budgetHours.getText();
					if (StringUtils.isNumeric(budgetHoursText) && !budgetHoursText.isEmpty())
						return Double.valueOf(budgetHoursText);
					return null;
				}			
			});
			
			dialog.getRootPane().setDefaultButton(okButton);
			
		}

		private JTextField initializeBudgetHours() {
			budgetHours = new JTextField(5);
			budgetHours.setEnabled(false);
			return budgetHours;
		}

		private JCheckBox createBudgetCheckbox() {
			final JCheckBox budgetCheckBox = new JCheckBox("Budget");
			budgetCheckBox.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					budgetCheckBox.setEnabled(budgetCheckBox.isSelected());				
				}			
			});
			
			return budgetCheckBox;
		}

		private void doCancel(JDialog dialog) {
			dialog.setVisible(false);
		}
	
		
		private JDialog createDialog(Maybe<Long> time) {
			
			JDialog dialog = new JDialog();
			final JPanel fieldsPanel = buildFieldsPanel();
			final JPanel buttonBar = buildOkCancelBar(dialog, time);
			
			dialog.setLayout(new BorderLayout());
			dialog.add(fieldsPanel, BorderLayout.CENTER);
			dialog.add(buttonBar, BorderLayout.SOUTH);
			
			
			dialog.setPreferredSize(new Dimension(380,170));
			
			
			dialog.setResizable(false);
			dialog.setTitle(TITLE);		
			dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			dialog.setModal(true);
			
			enableCancelOnEsc(dialog);
			
			dialog.pack();
			
			return dialog;
			
		}
	}
	
	
}
