
package model;

import java.util.*;
import java.lang.Math;

import model.annotation.Annotation;
import model.modelobject.PrototypeModelObject;
import model.point.Point;
import model.topologies.Singularity
import model.Model;

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

	/** Reading Frames for nucleotide to amino acid comparisons.
	 *
	 * <code>PLUS_ONE</code> should be the default usage if the idea
	 * of reading frame is not neccesary (such as comparing sizes.
	 *
	 * <code>NO_FRAME</code> will void any arithemic or logic based
	 * on reading frames.
	 */
	enum ReadingFrame {
		/** Negative direction, offset two steps to the left of the last
		 * nucleotide.
		 */
		MINUS_TRHEE (-3),
		/** Negative direction, offset one step to the left of the last
		 * nucleotide.
		 */
		MINUS_TWO (-2),
		/** Negative direction, starting at the last nucleotide */
		MINUS_ONE (-1),
		/** No frame enumeration will void/block any reading frame
		 * interpretation logic and arithemtic.
		 */
		NO_FRAME (0),
		/** Positive direction, starting at the first nucleotide */
		PLUS_ONE (1),
		/** Positive direction, starting at the second nucleotide */
		PLUS_TWO (2),
		/** Positive direction, starting at the thrid nucleotide */
		PLUS_THREE (3);

		/** Stores the reading frame value */
		private int readingFrame;

		/** Constructor assigning reading frame value */
		public ReadingFrame(int rf) {
		
			this.readingFrame = rf;
			
		}

		/**
		 * Gets the value of the reading frame
		 *
		 * @return
		 * 			The value of the reading frame
		 */
		public int getFrame() {

			return readingFrame;
		}
	]

	class SequenceWalker implements Iterator {

		/** Walking distance each iteration */
		private int step = 2;

		/** The slice size */
		private int sliceSize = 1;

		/** The treatment of incomplete slices (the last one if any) */
		private boolean allowIncomplete = false;

		/** The current point */
		private int curPos = 1;

		/** The starting point */
		private int startPos = 1;

		/** The next postion as a point*/
		private Point nextPos;
		//private Integer nextPos = new Integer();

		private void initNextPoint() {

			Orphan top = new Orphan();
			nextPoint = new Point(Sequence, top, 1);
		}

		SequenceWalker(int pos) {
			this.pos = pos;
			this.startPos = pos;
			initNextPoint();
		}

		SequenceWalker(int pos, int step) {
			this.pos = pos;
			this.startPos = pos;

			if (step == 0) {
				throw new IllegalArgumentException("Steps must be non-zero");
			}
			this.step = step;

			initNextPoint();
		}

		public boolean hasNext() {

			nextPos = curPos + step;
			if (nextPos < 0 &&
					Sequence.physicalShape.equals(
						Sequence.PhysicalShape.MODE_LINEAR)) {

				return false;
			} else if (nextPos < 0 &&
					Sequence.physicalShape.equals(
						Sequence.PhysicalShape.MODE_CIRCULAR)) {

				nextPos = 

		}

		public String next() {

		}

		public void remove() {

			throws new UnsupportedOperationException(
					"Removing not allowed");

		}
	}

	/** The instance's type of sequence (nucleotide or amino acid) */
	private SequenceType sequenceType = sequenceType.TYPE_UNKNOWN;

	/** The instance's physical shape type (cirular or linear) */
	private PhysicalShape physicalShape = PhysicalShape.MODE_LINEAR;

	/** The model it belongs to */
	private Model model;

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

}
