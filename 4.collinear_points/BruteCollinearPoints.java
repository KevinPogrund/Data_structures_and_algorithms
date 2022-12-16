import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private final Point[] points;
    private int numberOfSegments;

    public BruteCollinearPoints(Point[] points) {
        checkPoints(points);
        this.points = points.clone();
        this.segments = new LineSegment[2];
        this.numberOfSegments = 0;

        Arrays.sort(this.points);

        for (int i = 0; i < this.points.length-3; i++) {
            for (int j = 0; j < this.points.length-2; j++) {
                for (int k = 0; k < this.points.length-1; k++) {
                    for (int l = 0; l < this.points.length; l++) {
                        double iToJ = this.points[i].slopeTo(this.points[j]);
                        double jToK = this.points[j].slopeTo(this.points[k]);
                        double kToL = this.points[k].slopeTo(this.points[l]);
                        if(iToJ==jToK&& jToK==kToL){
                            enqueue(new LineSegment(this.points[i], this.points[l]));
                            this.points[i].drawTo(this.points[l]);
                            StdDraw.show();
                        }
                    }

                }

            }
        }

    }    // finds all line segments containing 4 points

    private void enqueue(LineSegment seg){
        if(seg==null) throw new IllegalArgumentException();
        if(this.numberOfSegments ==this.segments.length) resize(2*this.segments.length);
        this.segments[this.numberOfSegments++]=seg;
    }

    private void resize(int size){
        assert size>= this.numberOfSegments;
        LineSegment[] temp = new LineSegment[size];
        System.arraycopy(this.segments, 0, temp, 0, this.numberOfSegments);
        this.segments = temp;
    }
    public int numberOfSegments() {
        return this.numberOfSegments;
    }    // the number of line segments

    private void checkPoints(Point[] point) {

        if (point == null) throw new NullPointerException();
        for (int i = 0; i < point.length; i++) {
            if (point[i] == null) throw new IllegalArgumentException();
            for (int j = 0; j < point.length; j++) {
                if (i != j && point[i].compareTo(point[j]) == 0) throw new IllegalArgumentException();
            }
        }
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(this.segments,this.numberOfSegments);
    }     // the line segments


}