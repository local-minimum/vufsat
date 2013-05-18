
package model;

import java.util.*;
import java.lang.Math;

import model.Annotation;
import model.PrototypeModelObject;

public class Sequence extends PrototypeModelObject {

	private final static int TYPE_UNKNOWN = 0;
	private final static int TYPE_AMINOACID = 1;
	private final static int TYPE_NUCLEOTIDE = 2;

	private final static int AA_TO_NT = 3;

	private String sequence;
	private int sequenceType = TYPE_UNKNOWN;
	private Collection<Annotation> annotations = new ArrayList<>();

	public ArrayList<PrototypeModelObject> getChildren() {
		return annotations;
	}

	public int getSize() {
		return sequence.length();
	}

	public int getSizeAsAminoAcid() {
		if (sequenceType == TYPE_AMINOACID) {
			return getSize();
		} else if (sequenceType == TYPE_NUCLEOTIDE) {
			return (int) Math.ceil(getSize() / AA_TO_NT);
		} else {
			return -1;
		}
	}

	public int getSizeAsNucleotide() {
		if (sequenceType == TYPE_NUCLEOTIDE) {
			return getSize();
		} else if (sequenceType == TYPE_AMINOACID) {
			return getSize() * AA_TO_NT;
		} else {
			return -1;
		}
	}

}
