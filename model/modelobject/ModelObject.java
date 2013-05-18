package model;
import java.util.*;

public interface ModelObject {

	public ArrayList<PrototypeModelObject> getChildren();
	public void setUpdatingObject(int id);
	public void setFinishedUpdatingObject(int id);
	public boolean setIdentifier(int id);
	public boolean setIdentifier();
	public boolean allowWrap();
	public int getSize();
}
