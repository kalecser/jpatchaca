package ui.swing.mainScreen;

import java.util.List;

import labels.LabelsSystem;

import org.picocontainer.Startable;

import statistics.ProjectVelocityCalculator;
import tasks.tasks.TaskView;
import ui.swing.users.LabelsUser;
import ui.swing.users.SwingTasksUser;
import basic.Formatter;
import basic.FormatterImpl;
import basic.Subscriber;




public class LabelsListSystemMediator implements Startable{


	public LabelsListSystemMediator(final LabelsSystem labelsSystem, final ProjectVelocityCalculator calculator,  final LabelsList list, final SwingTasksUser tasksUser, final LabelsUser labelsUser){
		
		list.assignTaskToLabelAlert().subscribe(new Subscriber() {
			public void fire() {
				labelsSystem.setLabelToTask(tasksUser.getSelectedTask(), labelsUser.getLabelToAssignTaskTo());
			}
		});
		
		
		list.setToolTipProvider(new LabelTooltipProvider(){

			public String getTipFor(String item) {
				final Formatter formatter = new FormatterImpl();
				 
				 final List<TaskView> tasks = labelsSystem.tasksInlabel(item);
				 final String numberOfTasks = formatter.formatNumber((double)tasks.size());
				 final Double pv = calculator.calculate(tasks);
				 
				 String pvRepresentation = "-";
				 if ( !pv.equals(Double.NaN) ) {
					 pvRepresentation = formatter.formatNumber(pv);
				 }
				 
				 final String toolTip = "<html>" +
				 					"<b>" + item + "</b>" +
				 					"<br>There are " + numberOfTasks + " tasks in the label" + 
				 					"<br>PV: " + pvRepresentation +
				 					"</html>";
				 
				 return toolTip;
			}});
		
	}


	public void stop() {}


	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	
}
