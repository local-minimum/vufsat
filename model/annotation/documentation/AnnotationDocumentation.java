package model.annotation.documentation;

public class AnnotationDocumentation {

	public DocumentationField publication = new DocumentationField();
	public DocumentationField link = new DocumentationField();
	public DocumentationField title = new DocumentationField();
	public DocumentationField caption = new DocumentationField();
	public DocumentationField doi = new DocumentationField();
	
	public AnnotationDocumentation() {
	}

	/*
	public boolean hasOriginalState = true;

	public void setManipulated() {
		hasOriginalState = false;
	}

	public boolean getOriginalState() {
		return hasOriginalState;
	}
	*/

}

class DocumentationField {

	private boolean isSet = false;
	private String data;

	public void set(String s) {
		data = s;
		isSet = true;
	}

	public String get() {

		if (isSet) {
			return data;
		} else {
			return null;
		}
	}

	public void unset() {
		isSet = false;
	}
}

