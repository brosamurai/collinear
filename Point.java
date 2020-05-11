/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double deltaY = that.y - this.y;
        double deltaX = that.x - this.x;

        // if two points are the same
        if (deltaX == 0 && deltaY == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        // if vertical line
        else if (deltaX == 0) {
            return Double.POSITIVE_INFINITY;
        }
        // if horizontal
        else if (deltaY == 0) {
            return +0.0;
        }
        else return deltaY / deltaX;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value 0 if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (this.x < that.x) {
            return -1;
        }
        else if (this.x > that.x) {
            return +1;
        }
        // if x values are the same
        else {
            if (this.y > that.y) return +1;
            else if (this.y < that.y) return -1;
            else return 0;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     * //
     */
    public Comparator<Point> slopeOrder() {
        return new BySlope(this);
    }

    // TODO: use Point.this rather than passing this through the constructor?
    private static class BySlope implements Comparator<Point> {
        private final Point current;

        public BySlope(Point point) {
            this.current = point;
        }

        public int compare(Point o1, Point o2) {
            if (current.slopeTo(o1) == current.slopeTo(o2)) return 0;
            else if (current.slopeTo(o1) > current.slopeTo(o2)) return +1;
            else return -1;
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point point1 = new Point(0, 3);
        Point point2 = new Point(0, 2);
        Point point3 = new Point(3, 1);
        Point point4 = new Point(2, 1);
        Point point5 = new Point(1, 1);
        Point point6 = new Point(1, 1);
        Point point7 = new Point(1, 1);
        Point point8 = new Point(1, 1);
        Point point9 = new Point(0, 2);
        Point point10 = new Point(0, 2);
        Point point11 = new Point(0, 2);

        Point[] points = new Point[11];
        points[0] = point1;
        points[1] = point2;
        points[2] = point3;
        points[3] = point4;
        points[4] = point5;
        points[5] = point6;
        points[6] = point7;
        points[7] = point8;
        points[8] = point9;
        points[9] = point10;
        points[10] = point11;


        Arrays.sort(points);

        // System.out.println("Before sort:");
        // for (Point p : points) {
        //     System.out.println(p.x + ", " + p.y);
        // }
        //
        // System.out.println("After sort:");
        // Arrays.sort(points, point1.slopeOrder());
        // for (Point p : points) {
        //     System.out.println(p.x + ", " + p.y);
        // }
    }
}
