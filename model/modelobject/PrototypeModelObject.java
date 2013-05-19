/**
 * PrototypeModelObject
 *
 * Creates an abstract class, which purpose is to report general
 * status and feature of the model exposed to all model objects
 */

package model.modelobject;

import java.util.*;
import model.modelobject.ModelObject;

public abstract class PrototypeModelObject implements ModelObject {

	private static boolean modelUpdateRunning = false;
	private static Collection<Integer> modelUpdatingObjects = 
		new HashSet<Integer>();
	private static Collection<Integer> identifiers = new HashSet<Integer>();
	private static int identifierNext;

	private int identifier;
	private boolean hasAcquiredId = false;

	/** The default shape is linear */
	private PhysicalShape physicalShape = 
		ModelObject.PhysicalShape.SHAPE_LINEAR;

	public PrototypeModelObject() {
		hasAcquiredId = setIdentifier();
	}

	public PrototypeModelObject(int id) {
		hasAcquiredId = setIdentifier(id);
	}

	@Override
	public PhysicalShape getShape() {
		return physicalShape;
	}

	@Override
	public void setShape(PhysicalShape shape) {
		physicalShape = shape;
	}

	@Override
	public boolean allowWrap() {
		return physicalShape.equals(PhysicalShape.SHAPE_CIRCULAR);
	}

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

	@Override
	public boolean setIdentifier(int id) {
		boolean ret = identifiers.add(id);
		if (ret && id >= identifierNext) {
			identifier = id;
			identifierNext = id + 1;
		}
		return ret;
	}

	@Override
	public boolean setIdentifier() {
		return setIdentifier(identifierNext);
	}

}


