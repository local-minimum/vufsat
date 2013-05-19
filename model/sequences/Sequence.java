
package model.sequence;

import java.util.*;
import java.lang.Math;

import model.annotation.Annotation;
import model.modelobject.PrototypeModelObject;
import model.point.Point;
import model.topologies.Singularity;
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
	}

	class SequenceWalker implements Iterator<String> {

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

		/** A point to test postions with */
		private Point testPoint;
		
		/** Slice String Builder. */
		private StringBuilder nextSlice;

		/** Says if next has been set */
		private boolean nextIsSet = false;

		SequenceWalker(int pos) {
			setStartPos(pos);
			this.nextSlize = new StringBuilder(this.sliceSize);
			initTestPoint();
		}

		SequenceWalker(int pos, int step) {
			setStartPos(pos);
			this.nextSlize = new StringBuilder(this.sliceSize);
			setStep(step);
			initTestPoint();
		}

		SequenceWalker(int pos, int step, int sliceSize, 
				boolean allowIncomplete) {
			setStep(step);
			setStartPos(pos);
			if (sliceSize < 1) {
				throw new IllegalArgumentException("Slices must be positive");
			}
			this.sliceSize = sliceSize;
			this.allowIncomplete = allowIncomplete;
			this.nextSlize = new StringBuilder(this.sliceSize);

			initTestPoint();

		}

		/** Helper method for constructor to init the testPoint */
		private void initTestPoint() {

			Orphan top = new Orphan();
			testPoint = new Point(Sequence, top, 1, step);
		}


		/** Helper method that verifies constructor start positions. */
		private void setStartPos(int pos){
			if (pos % 2 != 1) {
				throw new IllegalArgumentException(
						"Positions must be on (odd), not between (even)");
			}
			this.pos = pos;
			this.startPos = pos;

		}

		/** Helper method that verifies constructor step size. */
		private void setStep(int step) {

			if (step == 0 || step % 2 != 0) {
				throw new IllegalArgumentException(
						"Steps must be even non-zero values");
			}
			this.step = step;

		}

		/**
		 * Produces the next slice.
		 *
		 * A slice is set, even if not all conditions are met.
		 * The method also lets the iterator know it has calculated
		 * the next iteration thing so it doesn't have to be done twice
		 */
		private void setNextSlize() {

			boolean foundProblem = false;
			int prevPos = curPos;

			nextSlice.delete(0, nextSlice.length());

			foundProblem = !testPoint.setPos(curPos + step);

			if (!foundProblem) {
				for (int i = 0; i < sliceSize; i++) {
					foundProblem = !testPoint.movePos(1);
					if (foundProblem ||
							testPoint.getPos() == startPos ||
							((!testPoint.getDidWrap()) && (
								(testPoint.getPos() > startPos) !=
								(prevPos > startPos)))){

						break;

					} else {
						nextSlice.append(Sequence.getCharAt(testPoint));
					}
					prevPos = curPos.getPos();
				}
			}

			nextIsSet = true;
		}

		public boolean hasNext() {

			if (!nextIsSet) {
				setNextSlizePoints();
			}

			return ((nextSlice.length() == sliceSize) ||
					(nextSlice.length() > 0 && allowIncomplete));

		}

		public String next() {

			if (!nextIsSet) {
				setNextSlizePoints();
			}
			
			curPos = testPoint.getPos();

			nextIsSet = false;

			return nextSlice.toString();

		}

		public void remove() {

			throw new UnsupportedOperationException(
					"Removing not allowed");

		}
	}

	/** The instance's type of sequence (nucleotide or amino acid) */
	private SequenceType sequenceType = sequenceType.TYPE_UNKNOWN;

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

	public Iterator<String> getSequenceWalker(int pos) {
		return new SequenceWalker(pos);
	}

	public Iterator<String> getSequenceWalker(int pos, int stepSize) {
		return new SequenceWalker(pos, stepSize);
	}

	public Iterator<String> getSequenceWalker(int pos, int stepSize,
			int sliceSize, boolean allowIncomplete) {
		return new SequenceWalker(pos, stepSize, sliceSize, allowIncomplete);
	}

	/**
	 * Returns the size in model-space.
	 *
	 * If the sequence is of type <code>PhysicalShape.SHAPE_LINEAR</code>
	 * it is considered to have <i>inbetween</i>-positions both before
	 * and after the sequence. For <code>PhysicalShape.SHAPE_CIRCULAR</code>
	 * before and after is the same logical position. So for that reason,
	 * a linear sequence is one longer.
	 *
	 * @return
	 * 			The size
	 */
	public int getSize() {
		int val = getShape().equals(PhysicalShape.SHAPE_LINEAR) ? 1: 0;
		return sequence.length() * 2 + val;
	}

	/**
	 * Returns character at the position that a point is pointing at.
	 *
	 * Note that only point pointing at <i>On</i> positions are allowed
	 *
	 * @param p
	 * 			A <code>Point</code> that annotates present sequence
	 * @return
	 * 			Character at the <code>Point</code>s position.
	 */
	public char getCharAt(Point p) {

		int pos = (int) p.getPos() / 2;

		if (p.getPos() % 2 == 0) {
			throw new StringIndexOutOfBoundsException(
					"The characters are located at odd positions," +
					"invalid %n position for character".format(p.getPos()));
		} else if (p.annotatesObject(this) == false) {
			throw new IllegalArgumentException(
					"The point does not annotate this sequence.");
		}

		return sequence.charAt(pos);

	}
}
