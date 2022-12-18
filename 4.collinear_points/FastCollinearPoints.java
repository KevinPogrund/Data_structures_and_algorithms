import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        checkPoints(points);
        Point[] pointsRef = Arrays.copyOf(points, points.length);
        Point[] copyOfPoints = Arrays.copyOf(points, points.length);
        ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();
        Arrays.sort(pointsRef);
        for (int i = 0; i < pointsRef.length; i++) {
            Point origin = pointsRef[i];
            Arrays.sort(copyOfPoints);
            Arrays.sort(copyOfPoints, origin.slopeOrder());

            int numPoints = 1;
            Point lineStart = null;

            for (int j = 0; j < copyOfPoints.length - 1; j++) {
                if (copyOfPoints[j].slopeTo(origin) == copyOfPoints[j + 1].slopeTo(origin)) {
                    numPoints++;
                    if (numPoints == 2) {
                        lineStart = copyOfPoints[j];
                        numPoints++;
                    } else if (numPoints >= 4 && j + 1 == copyOfPoints.length - 1) {
                        if (lineStart.compareTo(origin) == 1)
                            segmentList.add(new LineSegment(origin, copyOfPoints[j + 1]));
                        numPoints = 1;
                    } else {
                        numPoints = 1;
                    }
                }
            }
            segments = segmentList.toArray(new LineSegment[segmentList.size()]);
        }
    } // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return this.segments.length;
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

    public LineSegment[] segments() {
        return Arrays.copyOf(this.segments, this.segments.length);
    }            // the line segments
}