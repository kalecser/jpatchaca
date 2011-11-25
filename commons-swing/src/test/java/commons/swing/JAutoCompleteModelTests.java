package commons.swing;

import java.util.List;

import org.junit.Before;

public class JAutoCompleteModelTests extends JAutoCompleteAbstractTests{

	
	private JAutoCompleteTextFieldModel autoCompleteTextFieldModel;
	
	@Override
	@Before
	public void setup(){
		super.setup();
		autoCompleteTextFieldModel = new JAutoCompleteTextFieldModel(elements);
	}

	@Override
	protected List<? extends Object> possibilitiesFor(String criteria) {
		return autoCompleteTextFieldModel.possibilitiesFor(criteria);
	}
	
	

}
