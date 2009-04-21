package ui.swing.mainScreen.tasks;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import lang.Maybe;

import org.apache.commons.lang.StringUtils;

import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import ui.swing.presenter.ActionPane;
import ui.swing.presenter.Presenter;
import ui.swing.presenter.UIAction;
import ui.swing.presenter.ValidationException;
import basic.Formatter;
import basic.NonEmptyString;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


public class TaskScreenController{

	private static final long serialVersionUID = 1L;
	public static final String TITLE = "Task edition";
	
	private final Formatter formatter;
	private final TaskScreenModel model;
	private final Presenter presenter;
	

	public TaskScreenController(Formatter formatter, TaskScreenModel model, Presenter presenter){			
		this.model = model;
		this.formatter = formatter;
		this.presenter = presenter;
	}
	
	public void createTaskStarted(long time) {
		internalShow(null,Maybe.wrap(time));
	}	
	
	public void editSelectedTask() {
		TaskView selectedTask = model.selectedTask();
		if (selectedTask == null)
			internalShow(((Maybe<TaskView>)null), null);
		else
			internalShow(Maybe.wrap(selectedTask), null);
	}

	public void createTask() {
		internalShow(null, null);
	}
	
	private void internalShow(final Maybe<TaskView> task, final Maybe<Long> start) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				presenter.showOkCancelDialog(new TaskScreenDialog(task, start), TITLE);		
			}
		});
	}

	class TaskScreenDialog implements ActionPane{
		private static final long serialVersionUID = 1L;
		
		private JTextField taskNameTextBox;
		private JCheckBox budgetCheckBox;
		private JTextField budgetHours;
		private TaskView taskView;

		private final Maybe<TaskView> maybeTaskView;

		private final Maybe<Long> time;

		public TaskScreenDialog(Maybe<TaskView> maybeTaskView, Maybe<Long> time){
			this.maybeTaskView = maybeTaskView;
			this.time = time;
			
		}
		
		private JPanel createDialog(Maybe<Long> time) {
			
			taskNameTextBox = new JTextField(20);
			budgetCheckBox = new JCheckBox("Budget");
			budgetHours = new JTextField(5);		
			final FormLayout layout = new FormLayout("pref, 3dlu, left:pref");
			final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
			builder.append("Task name", taskNameTextBox);
			builder.nextLine();		
			builder.append(budgetCheckBox);				
			builder.append(budgetHours);
			
			final JPanel fieldsPanel1 = builder.getPanel();
			fieldsPanel1.setBorder(BorderFactory.createEmptyBorder(20,15,15,15));
			return fieldsPanel1;
			
		}

		@Override
		public JPanel getPanel() {
			JPanel dialog = createDialog(time);
			
			taskNameTextBox.requestFocus();	
			
			if (maybeTaskView == null){
				return dialog;
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
			
			taskNameTextBox.selectAll();
			
			return dialog;
		}
		
		private Double getBudget() {
			String budgetHoursText = budgetHours.getText();
			if (StringUtils.isNumeric(budgetHoursText) && !budgetHoursText.isEmpty())
				return Double.valueOf(budgetHoursText);
			return null;
		}			

		@Override
		public UIAction action() {
			return new UIAction() {
			
				@Override
				public void run() throws ValidationException {
					
					String taskName = taskNameTextBox.getText();
					if (taskName.equals(""))
						throw new ValidationException("Task name must not be empty");
					
					TaskData data = new TaskData(new NonEmptyString(taskName), getBudget());
					
					if (taskView != null)
						model.editTask(taskView, data);
					else if (time == null)
						model.createTask(data);
					else
						model.createTaskAndStart(data, time.unbox());
					
				}			
			};
		}
	}
	
	
}
