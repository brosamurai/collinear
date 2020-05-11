/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private int lineCount = 0;
    private Point[] tempPointsHolder;


    public FastCollinearPoints(Point[] points) {
        Point[] copyOfPoints = points.clone();
        tempPointsHolder = new Point[points.length * 2];

        for (Point p : points) {
            // sort by natural order and then slope order
            validateConstructor(copyOfPoints);
            Arrays.sort(copyOfPoints, p.slopeOrder());

            // start with 1 because invoking point is included already
            int pointsInLine = 1;

            for (int q = 1; q < copyOfPoints.length - 1; q++) {
                // if two neighbors are equal, line is still incomplete
                if (p.slopeTo(copyOfPoints[q]) == p.slopeTo(copyOfPoints[q + 1])) {
                    pointsInLine++;
                    // special case for end of array:
                    if (pointsInLine > 2 && q == copyOfPoints.length - 2) {
                        addLineConditionally(p, copyOfPoints[q - (pointsInLine - 2)],
                                             copyOfPoints[q + 1]);
                        pointsInLine = 1;
                    }
                }
                else if (pointsInLine > 2) {
                    addLineConditionally(p, copyOfPoints[q - (pointsInLine - 1)], copyOfPoints[q]);
                    pointsInLine = 1;
                }
                else pointsInLine = 1;
            }
        }
        createLineSegments();
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

    private void createLineSegments() {
        lineSegments = new LineSegment[lineCount];
        for (int i = 0; i < lineCount; i++) {
            lineSegments[i] = new LineSegment(tempPointsHolder[i * 2],
                                              tempPointsHolder[(i * 2) + 1]);
        }
        tempPointsHolder = null;
    }

    private void addLineConditionally(Point invoker, Point pointInQuestion, Point largestPoint) {
        // we add the line segment only if the invoking point is the start of the line segment
        // q must be the last point in the line, p could be the first, we need to check
        if (invoker.compareTo(pointInQuestion) < 0) {
            // the invoking point, p, is the first point in the line segment so we can add it
            tempPointsHolder[lineCount * 2] = invoker;
            tempPointsHolder[(lineCount * 2) + 1] = largestPoint;
            lineCount++;
        }
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
