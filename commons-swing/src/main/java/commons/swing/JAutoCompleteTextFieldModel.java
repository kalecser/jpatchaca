package commons.swing;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

public class JAutoCompleteTextFieldModel {

	private final List<? extends Object> elements;

	public JAutoCompleteTextFieldModel(List<? extends Object> elements) {
		
		if (elements ==null)
			throw new IllegalArgumentException("elements must not be null");
		
		this.elements = elements;
	}

	public List<? extends Object> possibilitiesFor(String string) {		
		
		if(string == null)
			return new ArrayList<Object>();
		
		Collator collator = Collator.getInstance();
		collator.setStrength(Collator.PRIMARY);
		
		List<Object> possibilities = new ArrayList<Object>();
		for (Object elementObj : elements) {
			String element = elementObj.toString();
			String startOfElement = element.substring(0, Math.min(string.length(), elementObj.toString().length()));
			boolean startsWith = collator.compare(string, startOfElement) == 0;
			if(startsWith){
				possibilities.add(elementObj);
			}
		}
		return possibilities;
	}

}
