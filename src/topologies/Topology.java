
package topologies;

import java.util.*;
import point.Point;
import modelobject.ModelObject;

public interface Topology {

	/**
	 * Get topology-children iterator
	 *
	 * @return
	 * 			Iterator that goes over the sub-topologies
	 */
	public Iterator<Topology> getChildIterator();

	/**
	 * Get points iterator
	 *
	 * @return
	 * 			Iterator that goes over the points
	 */
	public Iterator<Point> getPointIterator();

	/**
	 * Get points iterator for an object
	 *
	 * @param target
	 * 			The thing the points should point to
	 * @return
	 * 			Iterator
	 */
	public Iterator<Point> getPointIterator(ModelObject target);

	/**
	 * Returns iterator for points that matches a position on target.
	 * This is intended as a ways for interface and
	 * model to interact so that points can be
	 * interactively moved.
	 *
	 * @param target
	 * 			The annotee
	 * @param pos
	 * 			The position of the point
	 * @return
	 * 			The point matching the position on the annotation target.
	 */
	public Iterator<Point> getPointAt(ModelObject target, int pos);

	/**
	 * Adds a new point to the Topology.
	 * Generally intended to be used during the construction
	 * of the topology.
	 *
	 * Each topology may have a different add-mechanism.
	 * Some topologies may only hold one ponit.
	 * Some may hold none (Be meta-topologies)
	 * Some may hold two, for which order of addition matter
	 * Some may hold any number.
	 *
	 * @param p
	 * 			Point
	 * @return
	 * 			Success-statement
	 */
	public boolean addPoint(Point p);

	/**
	 * Removes a point from point collection
	 *
	 * @param p
	 * 			Point to be removed
	 * @return
	 * 			Success-statement
	 */
	public boolean removePoint(Point p);

	/**
	 * Adds a child-topology, invoking an heirarchy.
	 *
	 * @param top
	 * 			Child topologu
	 * @return
	 * 			Success-statement
	 */
	public boolean addTopology(Topology top);

	/**
	 * Removes a topology from the childrens list
	 *
	 * @param top
	 * 			Child topology to remove
	 * @return
	 * 			Success-statement
	 */
	public boolean removeTopology(Topology top);

	/**
	 * Sets a caption
	 *
	 * @param s: A caption
	 */
	public void setCaption(String s);

	/**
	 * Gets the caption
	 *
	 * @return captionn
	 */
	public String getCaption();

	/**
	 * For point to check if move they're attempting is
	 * allowed by the topology
	 *
	 * The point has first made sure that the position is
	 * a valid point-position.
	 *
	 * @param p
	 * 			Point
	 * @param pos
	 * 			New position
	 * @return
	 * 			Success-statement
	 */
	public boolean allowMove(Point p, int pos);
}
