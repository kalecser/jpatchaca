package labels.labels;

import org.apache.commons.lang.Validate;
import org.reactive.Signal;
import org.reactive.Source;

public class SelectedLabel {

	private final Source<String> subject;

	public SelectedLabel(){
		subject = new Source<String>(LabelsHome.ALL_LABEL_NAME);
	}
	
	public void update(String selected){
		Validate.notNull(selected);
		subject.supply(selected);
	}
	
	public Signal<String> selectedLabel(){
		return subject;
	}

	public String selectedLabelCurrentValue() {
		return selectedLabel().currentValue();
	}
	
}
