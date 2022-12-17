import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private final Point[] points;
    private int numberOfSegments;

    public FastCollinearPoints(Point[] points) {
        checkPoints(points);
        this.points = points.clone();
        this.segments = new LineSegment[2];
        this.numberOfSegments = 0;
        LinkedList<Point> collinearPoints = new LinkedList<Point>();

        for (Point point : this.points) {
            Arrays.sort(this.points, point.slopeOrder());
            double prevSlope = 0.0;

            for (int i = 0; i < this.points.length; i++) {
                double currentSlope = point.slopeTo(this.points[i]);
                if (i == 0 || prevSlope != currentSlope) {
                    if (collinearPoints.size() >= 3) {
                        this.enqueue(new LineSegment(collinearPoints.getFirst(), collinearPoints.getLast()));
                        collinearPoints.getFirst().drawTo(collinearPoints.getLast());
                        StdDraw.show();
                    }
                    collinearPoints.clear();
                }
                collinearPoints.add(this.points[i]);
                prevSlope = currentSlope;
            }
        }
    } // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return this.numberOfSegments;
    }        // the number of line segments

    private void checkPoints(Point[] point) {

        if (point == null) throw new IllegalArgumentException();
        for (int i = 0; i < point.length; i++) {
            if (point[i] == null) throw new IllegalArgumentException();
            for (int j = 0; j < point.length; j++) {
                if (i != j && point[i].compareTo(point[j]) == 0) throw new IllegalArgumentException();
            }
        }
    }

    private void enqueue(LineSegment seg) {
        if (seg == null) throw new IllegalArgumentException();
        if (this.numberOfSegments == this.segments.length) resize(2 * this.segments.length);
        this.segments[this.numberOfSegments++] = seg;
    }

    private void resize(int size) {
        assert size >= this.numberOfSegments;
        LineSegment[] temp = new LineSegment[size];
        System.arraycopy(this.segments, 0, temp, 0, this.numberOfSegments);
        this.segments = temp;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(this.segments, this.numberOfSegments);
    }            // the line segments
}