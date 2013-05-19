package model.modelobject;
import java.util.*;

public interface ModelObject {

	/** Physical Shape */
	enum PhysicalShape {
		/** Physical Shape: Linear Sequence */
		SHAPE_LINEAR,
		/** Physical Shape: Circular Sequence */
		SHAPE_CIRCULAR;
	}

	/** Returns the shape state of the object.
	 *
	 * @return
	 * 			The shape
	 */
	public PhysicalShape getShape();

	/** Sets the shape state of the object.
	 *
	 * @param shape
	 * 			The shape
	 */
	public void setShape(PhysicalShape shape);

	/** Checks if object allows for wrapping.
	 *
	 * That is if the object has <code>PhysicalShape.SHAPE_CIRCULAR</code>
	 *
	 * @return
	 * 			If object is allowing wrapping
	 */
	public boolean allowWrap();

	public ArrayList<ModelObject> getChildren();
	public void setUpdatingObject(int id);
	public void setFinishedUpdatingObject(int id);
	public boolean setIdentifier(int id);
	public boolean setIdentifier();
	public int getSize();
}
