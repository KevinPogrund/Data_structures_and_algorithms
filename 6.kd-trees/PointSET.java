import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.HashSet;
import java.util.Set;


public class PointSET {
    private SET<Point2D> set;

    public PointSET() {
        this.set = new SET<>();
    }

    // construct an empty set of points
    public boolean isEmpty() {
        return set.isEmpty();
    }                     // is the set empty?

    public int size() {
        return set.size();
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!contains(p)) set.add(p);
    }              // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }            // does the set contain point p?

    public void draw() {
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0);
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }                      // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Set<Point2D> result = new HashSet<>();
        for (Point2D p : set) {
            if (rect.contains(p)) result.add(p);
        }
        return result;
    }            // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        Point2D nearestPoint = null;
        double shortestDistance = Double.MAX_VALUE;
        for (Point2D point : set) {
            if (point.distanceTo(p) < shortestDistance) {
                nearestPoint = point;
                shortestDistance = point.distanceTo(p);
            }
        }
        return nearestPoint;
    }           // a nearest neighbor in the set to point p; null if the set is empty

    // unit testing of the methods (optional)
}