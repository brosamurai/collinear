/* *****************************************************************************
 *  Description: Brute force solution to finding collinear points
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    private int lineCount = 0;
    private Point[] startAndEndPoints;


    // finds all line segments containing 4 collinear points exactly (no dups!)
    public BruteCollinearPoints(Point[] points) {
        validateConstructor(points);

        startAndEndPoints = new Point[points.length * points.length];

        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])
                                && points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
                            // we've found a collinear line segment of length 4
                            addPointsForLineSegment(points[p], points[q], points[r], points[s]);
                        }
                    }
                }
            }
        }
        createLineSegments();
    }

    private void createLineSegments() {
        lineSegments = new LineSegment[lineCount];
        // there shouldn't be any duplicate lines in the brute force method
        for (int i = 0; i < lineCount; i++) {
            lineSegments[i] = new LineSegment(startAndEndPoints[i * 2],
                                              startAndEndPoints[(i * 2) + 1]);
        }
    }

    private void addPointsForLineSegment(Point p, Point q, Point r, Point s) {
        Point[] pointsInLine = { p, q, r, s };
        startAndEndPoints[lineCount * 2] = pointsInLine[0];
        startAndEndPoints[(lineCount * 2) + 1] = pointsInLine[3];
        lineCount++;
    }

    // the number of line segments in the set
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // all the line segments in the set
    public LineSegment[] segments() {
        LineSegment[] copy = lineSegments.clone();
        return copy;
    }

    private void validateConstructor(Point[] points) {
        // check if constructor is null
        if (points == null) {
            throw new IllegalArgumentException("constructor cannot be null");
        }

        // check if constructor contains any null points
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("constructor cannot contain null points");
            }
        }

        // check if constructor contains any duplicates
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Constructor cannot have duplicates");
            }
        }
    }

    public static void main(String[] args) {
        // // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
