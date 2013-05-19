
package model;

import java.util.*;
import model.Model

abstract class AbstractTopology<O> implements Topology<O> {

	private String caption = "";

	private Collection<Topology> childTopologies = new ArrayList(1);
	private Collection<Point> points = new ArrayList(1);

	private Model model;

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
	public Iterator<Point> getPointIterator(O obj) {

		new Collection<Point> filteredPoints = new ArrayList(1);

		for (new point: points) {

			if (point.annotatesObject(obj)) {
				filteredPoints.add(point);
			}
		}

		return filteredPoints.iterator();
	}

	@Override
	public Iterator<Point> getPointAt(O target, int pos) {

		new Collection<Point> filteredPoints = new ArrayList(1);

		for (new point: points) {

			if (point.annotatesObject(obj) && (paint.getPos() == pos)) {

				filteredPoints.add(point);
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
		return topologies.add(top);

	}

	@Override
	public boolean removeTopology(Topology top) {

		//Defualt behaviour is to allow 
		return topologies.remove(top);

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

		int wrappedPos = p.checkWarp(pos);
		ModelObject target = p.getTarget();


		return true;
	}
}
