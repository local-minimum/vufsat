
package model.topologies;

import java.util.*;
import model.Model;
import model.point.Point;
import model.modelobject.ModelObject;

abstract class AbstractTopology implements Topology<ModelObject> {

	protected String caption = "";

	protected Collection<Topology> childTopologies = new ArrayList(1);
	protected Collection<Point> points = new ArrayList(1);

	protected Model model;

	public AbstractTopology(Model m) {

		model = m;
		m.addTopology(this);
	}

	@Override
	public Iterator<Topology> getChildIterator() {
		return childTopologies.iterator();
	}

	@Override
	public Iterator<Point> getPointIterator() {
		return points.iterator();
	}

	@Override
	public Iterator<Point> getPointIterator(ModelObject target) {

		Collection<Point> filteredPoints = new ArrayList(1);

		for (Point point: points) {

			if (point.annotatesObject(target)) {
				filteredPoints.add(point);
			}
		}

		return filteredPoints.iterator();
	}

	@Override
	public Iterator<Point> getPointAt(ModelObject target, int pos) {

		Collection<Point> filteredPoints = new ArrayList(1);

		for (Point point: points) {

			if (point.annotatesObject(target) && (point.getPos() == pos)) {

				filteredPoints.add(point);
			}

		}
		return filteredPoints.iterator();

	}

	@Override
	public boolean addPoint(Point p) {

		//Defualt behaviour is to allow 
		return points.add(p);
	}

	@Override
	public boolean removePoint(Point p) {

		//Defualt behaviour is to allow 
		return points.remove(p);

	}

	@Override
	public boolean addTopology(Topology top) {

		//Defualt behaviour is to allow 
		return childTopologies.add(top);

	}

	@Override
	public boolean removeTopology(Topology top) {

		//Defualt behaviour is to allow 
		return childTopologies.remove(top);

	}

	@Override
	public void setCaption(String s) {

		caption = s;

	}

	@Override
	public String getCaption() {

		return caption;

	}

	@Override
	public boolean allowMove(Point p, int pos) {

		/* CHECKING OF WRAPPING IS DONE BETWEEN THE POINT AND TARGET
		 * THIS PART SHOULD ONLY CHECK IF THE TOPLOGY HAS ANY INTERNAL
		 * RESTRICTIONS
		int wrappedPos = p.checkWarp(pos);
		ModelObject target = p.getTarget();
		
		return (wrappedPos == pos || 
				target.getShape().equals(
					ModelObject.PhysicalShape.SHAPE_CIRCULAR));
		*/

		return true;
	}
}
