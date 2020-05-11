/* *****************************************************************************
 *  Description: Brute force solution to finding collinear points
 **************************************************************************** */

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    private int lineCount = 0;
    private Point[] startAndEndPoints;


    // finds all line segments containing 4 collinear points exactly (no dups!)
    public BruteCollinearPoints(Point[] points) {
        Arrays.sort(points);
        validateConstructor(points);

        startAndEndPoints = new Point[points.length * 2];

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
        int j = 0;
        for (int i = 0; i < startAndEndPoints.length - 1; i = i + 2) {
            if (startAndEndPoints[i] == null) break;
            lineSegments[j] = new LineSegment(startAndEndPoints[i], startAndEndPoints[i + 1]);
            j++;
        }
    }

    private void addPointsForLineSegment(Point p, Point q, Point r, Point s) {
        Point[] pointsInLine = { p, q, r, s };
        if (lineCount == 0) {
            startAndEndPoints[0] = pointsInLine[0];
            startAndEndPoints[1] = pointsInLine[3];
        }
        else {
            startAndEndPoints[lineCount + 1] = pointsInLine[0];
            startAndEndPoints[lineCount + 2] = pointsInLine[3];
        }
        lineCount++;
    }

    // the number of line segments in the set
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // all the line segments in the set
    public LineSegment[] segments() {
        return lineSegments;
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
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Constructor cannot have duplicates");
            }
        }
    }

    public static void main(String[] args) {
        Point p0 = new Point(0, 0);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(3, 3);
        Point p5 = new Point(1, 3);
        Point p6 = new Point(0, 4);
        Point p7 = new Point(3, 1);
        // Point p8 = new Point(123, -37);

        Point[] points = { p3, p1, p0, p6, p2, p5, p7 };
        Arrays.sort(points);
        Arrays.sort(points, p0.slopeOrder());
        Arrays.sort(points);
        Arrays.sort(points, p2.slopeOrder());

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        // // read the n points from a file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // Point[] points = new Point[n];
        // for (int i = 0; i < n; i++) {
        //     int x = in.readInt();
        //     int y = in.readInt();
        //     points[i] = new Point(x, y);
        // }
        //
        // // draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();
        //
        // // print and draw the line segments
        // BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // for (LineSegment segment : collinear.segments()) {
        //     StdOut.println(segment);
        //     segment.draw();
        // }
        // StdDraw.show();
    }
}
