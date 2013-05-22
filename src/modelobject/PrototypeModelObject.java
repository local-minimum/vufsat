/**
 * PrototypeModelObject
 *
 * Creates an abstract class, which purpose is to report general
 * status and feature of the model exposed to all model objects
 */

package modelobject;

import java.util.*;
import modelobject.ModelObject;

public abstract class PrototypeModelObject implements ModelObject {

	//private static boolean modelUpdateRunning = false;
	//private static Collection<Integer> modelUpdatingObjects = 
	//	new HashSet<Integer>();
	//
	
	/** Collection of identifiers used so far */
	private static Collection<Integer> identifiers = new HashSet<Integer>();

	/** Next available identifier */
	private static int identifierNext;

	/** Instance's identifier */
	private int identifier;

	/** Check if id is set */
	private boolean hasAcquiredId = false;

	/** The default shape is linear */
	private PhysicalShape physicalShape = 
		ModelObject.PhysicalShape.SHAPE_LINEAR;

	/** Constructor when no parameter sets new id */
	public PrototypeModelObject() {
		hasAcquiredId = setIdentifier();
	}

	/** Constructor with id when loading saved stuff */
	public PrototypeModelObject(int id) {
		hasAcquiredId = setIdentifier(id);
	}

	/**
	 * Gets the current shape of the object.
	 *
	 * @return
	 * 			The shape
	 */
	@Override
	public PhysicalShape getShape() {
		return physicalShape;
	}

	/**
	 * Sets the current shape
	 *
	 * @param shape
	 * 			The new shape
	 */
	@Override
	public void setShape(PhysicalShape shape) {
		physicalShape = shape;
	}

	/**
	 * Answers if sequence is wrap-around or not.
	 * 
	 * The visualisation doesn't have to reflect the physical
	 * behaviour.
	 *
	 * @return
	 * 			If sequence allows wrap
	 */
	@Override
	public boolean getAllowsWrap() {
		return physicalShape.equals(PhysicalShape.SHAPE_CIRCULAR);
	}

	/*
	@Override
	public void setUpdatingObject(int id) {
		modelUpdatingObjects.add(id);
		modelUpdateRunning = false;
	}

	@Override
	public void setFinishedUpdatingObject(int id) {
		modelUpdatingObjects.remove(id);
		modelUpdateRunning = modelUpdatingObjects.size() == 0;
	}
	*/

	/**
	 * Sets next available identifier
	 */
	private void setFreeIdentifier() {

		identifierNext = 0;
		
		while (identifiers.contains(identifierNext)) {
			identifierNext++;
		}

	}


	/**
	 * Sets current identifier.
	 *
	 * TODO: This should not be public, it should only be invoked by the
	 * constructor.
	 *
	 * @param id
	 * 			The new identfier
	 * @return
	 * 			If new identifier was accepted
	 */
	@Override
	public boolean setIdentifier(int id) {

		boolean ret = identifiers.add(id);
		if (ret) {
			identifier = id;
			setFreeIdentifier();
		}
		return ret;
	}

	/**
	 * Sets current identifier using the next available
	 * identifier.
	 *
	 * @return
	 * 			If new identifier was accepted
	 */
	@Override
	public boolean setIdentifier() {
		return setIdentifier(identifierNext);
	}

}


