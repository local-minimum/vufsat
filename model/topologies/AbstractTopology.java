
package model;

import java.util.*;
import model.Model

abstract class AbstractTopology<O> implements Topology<O> {

	private final static int MAX_POINTS = 1;

	private int numberOfChildTopologies = 0;
	private int numberOfPoints = 0;

	private Collection<Topology> childTopologies = new ArrayList(1);
	private Collection<Point> points = new ArrayList(1);

	private Model model;

	public AbstractTopology(Model m) {

		model = m;
		m.addTopology(this);
	}

	/**
	 * Returns an iterator for all subpopologies
	 *
	 * @return
	 * 			Iterator
	 */
	public Iterator<Topology> getChildIterator() {
		return childTopologies.iterator();
	}

	/**
	 * Returns an iterator for all points
	 *
	 * @return
	 * 			Iterator
	 */
	public Iterator<Point> getPointIterator() {
		return points.iterator();
	}

	/**
	 * Returns an iterator for only those point annotating a specifc
	 * object.
	 *
	 * @param obj
	 * 			The object the points should annotate
	 * @return
	 * 			Iterator for the points
	 */
	public Iterator<Point> getPointIterator(O obj) {

		new Collection<Point> filteredPoints = new ArrayList(1);

		for (new point: points) {

			if (point.annotatesObject(obj)) {
				filteredPoints.add(point);
			}
		}

		return filteredPoints.iterator();
	}

	/**
	 * Returns a point at a specific position along the object
	 * belonging to this particular topology.
	 *
	 * FIXIT: If there are no points a null is returned.
	 * FIXIT: If there are multiple points valid, only one is returned
	 *
	 * @param target
	 * 			The annotated object (e.g. sequence)
	 * @param pos
	 * 			The position along the target
	 * @return
	 * 			The point at the position
	 */
	public Point getPointAt(O target, int pos) {

		new Collection<Point> filteredPoints = new ArrayList(1);

		for (new point: points) {

			if (point.annotatesObject(obj) && (paint.getPos() == pos)) {

				filteredPoints.add(point);
			}

		return filteredPoints.iterator();

	}
}
