package outpost.group8;
import java.util.ArrayList;

import outpost.sim.Pair;
import outpost.sim.Point;
import outpost.sim.movePair;

public class QCirc {

	Point center;
	double radius;
	ArrayList<Point> points;
	
	public QCirc(Point center, double radius) {
		this.center = center;
		this.radius = radius;
		points = new ArrayList<Point>();
	}
	
}
