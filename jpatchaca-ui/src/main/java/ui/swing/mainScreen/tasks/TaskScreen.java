package ui.swing.mainScreen.tasks;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.reactivebricks.commons.lang.Maybe;

import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import ui.swing.presenter.OkCancelPane;
import ui.swing.presenter.Presenter;
import basic.Formatter;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


public class TaskScreen{

	private static final long serialVersionUID = 1L;
	public static final String TITLE = "Task edition";
	
	private final Formatter formatter;
	private final TaskScreenModel model;
	private final Presenter presenter;
	

	public TaskScreen(Formatter formatter, TaskScreenModel model, Presenter presenter){			
		this.model = model;
		this.formatter = formatter;
		this.presenter = presenter;
	}
	
	private void show() {
		new Thread(){
			@Override
			public void run() {
				internalShow(null, null);
			}
		}.start();
		
	}
	
	private void show(final Long time) {
		new Thread() {
			@Override
			public void run() {
				internalShow(null,Maybe.wrap(time));
			}
		}.start();
	}
	
	private void show(final Maybe<TaskView> maybeTaskView) {
		new Thread(){
			@Override
			public void run() {
				internalShow(maybeTaskView, null);
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
	
	private void internalShow(Maybe<TaskView> task, Maybe<Long> start) {
		presenter.showOkCancelDialog(new TaskScreenDialog(task, start), TITLE);
	}

	class TaskScreenDialog implements OkCancelPane{
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
		
		private JTextField initializeBudgetHours() {
			budgetHours = new JTextField(5);
			return budgetHours;
		}

		private JPanel createDialog(Maybe<Long> time) {
			
			taskNameTextBox = new JTextField(20);
			budgetCheckBox = new JCheckBox("Budget");		
			initializeBudgetHours();
			
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
		public Runnable okAction() {
			return new Runnable() {
			
				@Override
				public void run() {
					TaskData data = new TaskData(taskNameTextBox.getText(), getBudget());
					
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
