/*
 * Point class slass is used to mark a position on
 * a display object.
 *
 * It links together the Annotation, the Topology
 * of said Annotation in which the point is a part,
 * and the Object begin annotated.
 *
 * The Point is accessable from the Annotation
 * for wheen the Annotation machinery needs to update
 * it.
 *
 * The point is accessible from the Topology for when
 * the view needs to update the positions.
 */

package point;

import topologies.Topology;
import modelobject.ModelObject;

/**
 * Point are the stuff that marks things
 */
final public class Point { 


	private Topology topology;
	private ModelObject target;

	/** The position. */
	private int pos;

	/** The size of stepping. */
	private int stepSize = 2;

	/** If last positioning of any type involved wrapping. */
	private boolean didWrap = false;
	
	/**
	 * Alternate constructor that also places
	 * the point on the target
	 *
	 * @param target
	 * 			The annotee
	 * @param top
	 * 			The topology that uses the point
	 * @param pos
	 * 			The position along the target
	 */
	public Point(ModelObject target, Topology top, int pos) {
		this.target = target;
		this.topology = top;
		this.pos = pos;
	}

	/**
	 * Alternate constructor that also places
	 * the point on the target
	 *
	 * @param target
	 * 			The annotee
	 * @param top
	 * 			The topology that uses the point
	 * @param pos
	 * 			The position along the target
	 * @param stepSize
	 *			Only positive values and values less than target size allowed
	 *			One means it will step "on", "between", "on", "between".
	 *			Two means it will step "on", "on", "on" (or on betweens)
	 */
	public Point(ModelObject target, Topology top, int pos, int stepSize) {
		this.target = target;
		this.topology = top;
		this.pos = pos;
		if (stepSize < 1) {
			throw new IllegalArgumentException(
					"Negative stepSize not allowed");
		} else if (stepSize >= target.getSize()) {
			throw new IllegalArgumentException(
					"stepSize larger than target");
		}
		this.stepSize = stepSize;
	}

	/**
	 * Gets the position in model-space
	 *
	 * @return
	 * 			The position of the point on the annotee
	 */
	public int getPos() {
		return this.pos;

	}

	/**
	 * Gets the target
	 *
	 * @return
	 * 			The target
	 */
	public ModelObject getTarget() {

		return target;
	}

	/**
	 * Checks if position is an inbetween position or not
	 *
	 * @return
	 * 			If it is inbetween
	 */
	public boolean getIsInbetween() {
		return ((getPos() % 2) == 0);
	}

	/**
	 * Sets a new position if allowed
	 *
	 * @param int
	 * 			New position
	 * @return
	 * 			Success-value
	 * 
	 */
	public boolean setPos(int p) {
		p = checkWarp(p);
		if (allowMove(p)) {
			pos = p;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if a target is pointed at by the point
	 *
	 * @param obj
	 * 			The object to test (sequence or alike)
	 * @return
	 * 			If topology is pointed at
	 */
	public boolean annotatesObject(ModelObject obj) {

		return this.target == obj; 
	}

	/**
	 * Checks if last movement was wrapped
	 *
	 * @return
	 * 			If it was
	 */
	public boolean getDidWrap() {
		return didWrap;
	}

	/**
     * Attempts to move point <code>step</code> steps.
     *
     * Note that the number of steps are in step units
     * and that the point internally deals with its stepSize
     *
     * @param step
     * 			The step size of the move
     * @return
     * 			Success-statement
     */
	public boolean movePos(int step) {
		return setPos(pos + stepSize * step);
	}

	/**
	 * Evaluates if move is allowed by the
	 * topologies using the point
	 *
	 * @param p
	 * 			Suggested new position
	 * @return
	 * 			If allowed
	 */
	private boolean allowMove(int p) {

		//Get absolute position according to wrap contditions
		p = checkWarp(p);

		return this.topology.allowMove(this, p);
	}

	/**
	 * Checks if annotee allows wrapping and invokes
	 * it if neccesary. If no wrap it makes sure point
	 * is in bounds.
	 *
	 * @param p
	 *			Suggested new position
	 * @return
	 * 			Potentially adjusted position
	 */
	private int checkWarp(int p) {

		int maxPos = target.getSize();

		didWrap = false;

		//If wrapping
		if (target.getAllowsWrap()) {

			//Make sure values are positive
			while (p < 0) {
				p += maxPos;
				didWrap = true;
			}

			//Invoke max overflow wrap
			if (maxPos > 0 && p >= maxPos) {
				p = p % maxPos;
				didWrap = true;

			} else if (maxPos == 0) {
				throw new ArithmeticException(
						"Wrap check only possible when target has a size");
			}

		//if not wrapping
		} else {

			if (p >= maxPos) {
				p = maxPos - 1;
			} else if (p < 0) {
				p = 0;
			}

		}

		return p;
	}
}
