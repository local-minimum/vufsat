
package model;

import java.util.*;
import java.lang.Math;

import model.Annotation;
import model.PrototypeModelObject;

public class Sequence extends PrototypeModelObject {

	/** Sequence Type */
	enum SequenceType {
		/** Type of Sequence: Unknown */
		TYPE_UNKNOWN,
		/** Type of Sequence: Amino Acid */
		TYPE_AMINOACID,
		/** Type of Sequence: Nucleotide */
		TYPE_NUCLEOTIDE;
	}

	/** Physical Shape */
	enum PhysicalShape {
		/** Physical Shape: Linear Sequence */
		MODE_LINEAR,
		/** Physical Shape: Circular Sequence */
		MODE_CIRCULAR;
	}

	/** Conversion factor for nucleotides to amino acids */
	private static final int NT_TO_AA_RATIO = 3;

	/** The instance's type of sequence (nucleotide or amino acid) */
	private SequenceType sequenceType = sequenceType.TYPE_UNKNOWN;

	/** The instance's physical shape type (cirular or linear) */
	private PhysicalShape physicalShape = PhysicalShape.MODE_LINEAR;

	/** The actual sequence-text */
	private String sequence;

	/** The annotations array */
	private Collection<Annotation> annotations = new ArrayList<>();

	/** 
	 * Returns an iterator for the annotations annotating the
	 * sequence.
	 *
	 * @return
	 * 			The iterator
	 */
	public Iterator<Annotation> getAnnotations() {
		return annotations.iterator();
	}

	/**
	 * Returns the size in model-space.
	 *
	 * If the sequence is of type <code>PhysicalShape.MODE_LINEAR</code>
	 * it is considered to have <i>inbetween</i>-positions both before
	 * and after the sequence. For <code>PhysicalShape.MODE_CIRCULAR</code>
	 * before and after is the same logical position. So for that reason,
	 * a linear sequence is one longer.
	 *
	 * @return
	 * 			The size
	 */
	public int getSize() {
		int val = physicalShape.equals(PhysicalShape.MODE_LINEAR) ? 1: 0;
		return sequence.length() * 2 + val;
	}

	/**
	 * Converts a model-value for the present sequence type
	 * into any other sequence type value.
	 * 
	 * Conversion from <code>SequenceType.TYPE_AMINOACID</code>
	 * into <code>SequenceType.TYPE_NUCLEOTIDE</code> for positions
	 * will give the starting nucleotide of the triplet forming the
	 * aminoacid as the position for <i>On</i> positions and the position
	 * between the last nucleotide of the preceeding triplet and the first
	 * nucleotide of the current triplet as the <i>Between</i> position.
	 *
	 * Conversion from <code>SequenceType.TYPE_NUCLEOTIDE</code> into
	 * <code>sequenceType.TYPE_AMINOACID</code> will for <i>On</i> positions
	 * give the same amino acid position for any of the three nucleotide
	 * positions comprising the amino acid position. For <i>Inbetween</i>
	 * positions 
	 *
	 * No other conversions are implemented at this stage
	 *
	 * @param toType
	 * 			The type of sequence to convert value into
	 * @param val
	 * 			The value to convert
	 * @return
	 * 			Corresponding value for a sequence to the <code>toType</code>
	 */
	private int valueConvert(SequenceType toType, int val) {

		if (sequenceType == toType) {

			return val;

		} else if (sequenceType.equals(SequenceType.TYPE_UNKNOWN)) {

			throws new IllegalStateException(
				"Conversion not supported when sequence type is %s".format(
					sequenceType.name()));

		} else if (toType.equals(SequenceType.TYPE_UNKNOWN)) {

			throws new ArithmeticException(
					"Cannot convert values into %s".format(
						toType.name()));
		}


		if (sequenceType.equals(SequenceType.TYPE_NUCLEOTIDE) &&
				toType.equals(SequenceType.TYPE_AMINOACID)) {

		} else if (sequenceType.equals(SequenceType.TYPE_AMINOACID) &&
				toType.equals(SequenceType.TYPE_NUCLEOTIDE)) {

		} else {

			throws new ArithmeticException(
					"Conversion from %s into %s not implemented".format(
						sequenceType.name(), toType.name()));

		}
	}

	/**
	 * Returns the size converted as if sequence was amino acids
	 * If sequence is nucleotide and sequence length is not a multiple of
	 * three, the amino acid length will be rounded up.
	 *
	 * @return
	 * 			The animo acid version size
	 */
	public int getSizeAsAminoAcid() {

		int val = physicalShape.equals(PhysicalShape.MODE_LINEAR) ? 1: 0;

		if (sequenceType.equals(SequenceType.TYPE_AMINOACID)) {
			return getSize();
		} else if (sequenceType.equals(SequenceType.TYPE_NUCLEOTIDE)) {
			return ((int) (sequence.length() / NT_TO_AA_RATIO)) * 2 + val;
		} else {
			throws new IllegalStateException(
				"Operation not supported for sequence type %s".format(
					sequenceType.name()));
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
