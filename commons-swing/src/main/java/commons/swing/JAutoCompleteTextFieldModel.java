package commons.swing;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

public class JAutoCompleteTextFieldModel {

	private final List<? extends Object> elements;
	private final Collator collator;

	public JAutoCompleteTextFieldModel(List<? extends Object> elements) {
		
		if (elements ==null)
			throw new IllegalArgumentException("elements must not be null");
		
		this.elements = elements;
		
		collator = Collator.getInstance();
		collator.setStrength(Collator.PRIMARY);
	}

	public List<? extends Object> possibilitiesFor(final String part) {		
		
		if(part == null)
			return new ArrayList<Object>();
		
		if (part.isEmpty())
			return new ArrayList<Object>();
		
		
		List<Object> possibilities = new ArrayList<Object>();
		for (Object elementObj : elements) {
			String element = elementObj.toString();
			
			String partOrNull = pickPartUsingCollatorOrNull(part, element);
			if(partOrNull != null){
				possibilities.add(element);
			}
		}
		return possibilities;
	}

	private String pickPartUsingCollatorOrNull(String part, String string) {
		for (int i = 0; i < string.length(); i++) {
			String pieceOfElement = string.substring(i, Math.min(i + part.length(), string.length()));
			if (collator.compare(part, pieceOfElement) == 0){
				return pieceOfElement;
			}
		}
		return null;
	}

}
