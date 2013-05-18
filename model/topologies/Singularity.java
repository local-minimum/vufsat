package model;

import model.Model;

class Singularity<O> extends AbstractTopology<O> {

	private static int maxPoints = 1;

	private int numberOfChildTopologies = 0;

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
