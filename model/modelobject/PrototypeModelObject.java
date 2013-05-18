/**
 * PrototypeModelObject
 *
 * Creates an abstract class, which purpose is to report general
 * status and feature of the model exposed to all model objects
 */

package model;

import java.util.*;
import model.ModelObject;

public abstract class PrototypeModelObject implements ModelObject {

	private static boolean modelUpdateRunning = false;
	private static Collection<Integer> modelUpdatingObjects = new HashSet<Integer>();
	private static Collection<Integer> identifiers = new HashSet<Integer>();
	private static int identifierNext;

	private int identifier;
	private boolean hasAcquiredId = false;

	public PrototypeModelObject() {
		hasAcquiredId = setIdentifier();
	}

	public PrototypeModelObject(int id) {
		hasAcquiredId = setIdentifier(id);
	}

	public void setUpdatingObject(int id) {
		modelUpdatingObjects.add(id);
		modelUpdateRunning = false;
	}

	public void setFinishedUpdatingObject(int id) {
		modelUpdatingObjects.remove(id);
		modelUpdateRunning = modelUpdatingObjects.size() == 0;
	}

	public boolean setIdentifier(int id) {
		boolean ret = identifiers.add(id);
		if (ret && id >= identifierNext) {
			identifier = id;
			identifierNext = id + 1;
		}
		return ret;
	}

	public boolean setIdentifier() {
		return setIdentifier(identifierNext);
	}

}


