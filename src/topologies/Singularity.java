package topologies;

import collector.Model;
import point.Point;

public class Singularity<O> extends AbstractTopology {

	protected static int maxPoints = 1;

	public Singularity(Model m) {

		super(m);
	}

	@Override
	public boolean addPoint(Point p) {

		if (points.size() < maxPoints) {

			return points.add(p);

		} else {

			return false;

		}
	}
}
