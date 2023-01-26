import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.*;

public class KdTree {
    private Node root;
    private int size = 0;

    public KdTree() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return this.size;
    }

    public void insert(Point2D p) {
        RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
        root = insert(root, p, true, r);
    }

    private Node insert(Node n, Point2D p, boolean vertical, RectHV r) {
        if (n == null) {
            size++;
            return new Node(p, vertical, r);
        } else {
            int cmp = n.compareTo(p);
            if (cmp > 0) {
                if (n.isVertical) r = new RectHV(r.xmin(), r.ymin(),
                        n.point.x(), r.ymax());
                else r = new RectHV(r.xmin(), r.ymin(),
                        r.xmax(), n.point.y());
                n.left = insert(n.left, p, !vertical, r);
            } else {
                if (n.isVertical) r = new RectHV(n.point.x(), r.ymin(),
                        r.xmax(), r.ymax());
                else r = new RectHV(r.xmin(), n.point.y(),
                        r.xmax(), r.ymax());
                n.right = insert(n.right, p, !n.isVertical, r);
            }
            return n;
        }
    }

    public boolean contains(Point2D p) {
        return contains(p, root);
    }

    private boolean contains(Point2D p, Node n) {
        if (n == null) return false;
        if (n.point.equals(p)) return true;
        if (n.isVertical) return contains(p, (p.x() < n.point.x() ? n.left : n.right));
        else return contains(p, (p.y() < n.point.y() ? n.left : n.right));
    }

    public void draw() {
        draw(root, 0.0, 0.0, 1.0, 1.0);
    }

    private void draw(Node n, double minX, double minY, double maxX, double maxY) {
        if (n == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(n.point.x(), n.point.y());
        if (n.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(n.point.x(), minY, n.point.x(), maxY);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(minX, n.point.y(), maxX, n.point.y());
        }
        if (n.left != null) {
            double mxx = n.isVertical ? n.point.x() : maxX;
            double mxy = !n.isVertical ? n.point.y() : maxY;
            draw(n.left, minX, minY, mxx, mxy);
        }
        if (n.right != null) {
            double mnx = n.isVertical ? n.point.x() : minX;
            double mny = !n.isVertical ? n.point.y() : minY;
            draw(n.right, mnx, mny, maxX, maxY);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> result = new ArrayList<>();
        explore(root, rect, result);
        return result;
    }

    private void explore(final Node n, final RectHV rect, final List<Point2D> result) {
        if (rect.contains(n.point)) result.add(n.point);
        if (n.left != null) {
            if (n.isVertical && n.point.x() >= rect.xmin()) explore(n.left, rect, result);
            else if (!n.isVertical && n.point.y() >= rect.ymin()) explore(n.left, rect, result);
        }
        if (n.right != null) {
            if (n.isVertical && n.point.x() <= rect.xmax()) explore(n.right, rect, result);
            else if (!n.isVertical && n.point.y() <= rect.ymax()) explore(n.right, rect, result);
        }
    }

    public Point2D nearest(Point2D p) {
        Point2D result = nearest(p, root, null);
        return result;
    }

    private Point2D nearest(final Point2D p, Node n, Point2D result) {
        double distance = n.point.distanceSquaredTo(p);
        double shortestDistance = Double.MAX_VALUE;
        if (result == null) {
            result = n.point;
            shortestDistance = distance;
        } else {
            shortestDistance = result.distanceSquaredTo(p);
            if (distance < shortestDistance) {
                result = n.point;
                shortestDistance = distance;
            }
        }
        boolean searchLeft = false, searchRight = false;
        if (n.left != null) {
            double leftDistance = n.left.rect.distanceSquaredTo(p);
            if (leftDistance < shortestDistance) searchLeft = true;
        }
        if (n.right != null) {
            double rightDistance = n.right.rect.distanceSquaredTo(p);
            if (rightDistance < shortestDistance) searchRight = true;
        }
        boolean leftFirst = true;
        if (n.isVertical) {
            if (p.x() > n.point.x()) leftFirst = false;
        } else {
            if (p.y() < n.point.y()) leftFirst = false;
        }
        if (leftFirst) {
            if (searchLeft) result = nearest(p, n.left, result);
            if (searchRight) result = nearest(p, n.right, result);

        } else {
            if (searchLeft) result = nearest(p, n.left, result);
            if (searchRight) result = nearest(p, n.right, result);
        }
        return result;
    }


    private static class Node implements Comparable<Point2D> {
        private Point2D point;
        private final boolean isVertical;
        private Node left;
        private Node right;
        private RectHV rect;

        //        public Node(Point2D p) {
//            point = p;
//        }
//
        public Node(Point2D p, boolean isVertical, RectHV rect) {
            this.point = p;
            this.isVertical = isVertical;
            this.rect = rect;
        }

        @Override
        public int compareTo(Point2D that) {
            if (isVertical) return Point2D.X_ORDER.compare(this.point, that);
            else return Point2D.Y_ORDER.compare(this.point, that);
        }
    }
}
