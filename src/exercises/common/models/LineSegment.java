package exercises.common.models;

import exercises.common.utils.Matrix;
import exercises.common.utils.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Line model representing a straight line defined by two vectors ``pStart`` and ``pEnd``.
 * Implements ifCanvasDrawable and is able to draw itself onto a Canvas, given its
 * GraphicContext.
 * Implements basic methods to verify if a given point lies on the line within
 * a specific tolerance.
 */
public class LineSegment extends AbstractGeometry2D implements ifCanvasDrawable {

    // Statics
    public static float tolerance = 1.0f;

    // Properties
    private float thickness = 1.0f;
    private Color color = Color.rgb(0, 0, 0);
    private Color selectedColor = Color.rgb(29, 255, 213);

    // State
    private boolean deleted = false;
    private boolean selected = false;

    /**
     * Constructor.
     * @param x1 X-Coordinate of start point
     * @param y1 Y-Coordinate of start point
     * @param x2 X-Coordinate of end point
     * @param y2 Y-Coordinate of end point
     */
    public LineSegment(double x1, double y1, double x2, double y2, World world) {
        super(world);
        Vector2D[] vertice = {
                new Vector2D(x1, y1), new Vector2D(x2, y2)
        };
        this.setVertices(vertice);
    }

    /**
     * Calculates if a given point defined by a vector ``pt`` lies within a 180 degrees angle pointing
     * into the direction of this line instance on both its ends.
     *
     * @param pt Vector2D to be checked.
     * @return True if point lies within 180 degrees on both ends, false otherwise
     */
    private boolean isPointWithinAngle(Vector2D pt) {
        Vector2D pStart = this.getLocalVertex(0);
        Vector2D pEnd = this.getLocalVertex(1);

        Vector2D lineVector1 = pEnd.difference(pStart);
        Vector2D lineVector2 = pStart.difference(pEnd);
        Vector2D pointVector1 = pt.difference(pStart);
        Vector2D pointVector2 = pt.difference(pEnd);

        return lineVector1.scalar(pointVector1) > 0 && lineVector2.scalar(pointVector2) > 0;
    }

    /**
     * Calculates if a given point defined by a vector ``pt`` lies in the same line as this instance.
     * It does so by calculating the determinant of the point vector and the line vector and by dividing it
     * by the length of the line vector. This gives the distance of the given point from this line instance.
     * A tolerance of ``tolerance`` pixels will be taken into account.
     *
     * @param pt Vector2D to the point that should be checked.
     * @return True if distance of point from line is within tolerance, false otherwise.
     */
    private boolean isPointInLine(Vector2D pt) {
        Vector2D pStart = this.getLocalVertex(0);
        Vector2D pEnd = this.getLocalVertex(1);

        Vector2D lineVector = pEnd.difference(pStart);
        Vector2D pointVector = pt.difference(pStart);

        return Math.abs(pointVector.determinant(lineVector)) / lineVector.getLength() <= LineSegment.tolerance;
    }

    // ========================== ifCanvasDrawable Methods ===========================
    // ===============================================================================
    /**
     * Checks if a given point lies on a line. This operation involves two steps:
     * 1. Check if the point lies within 180 degrees facing this line instance on both ends
     * 2. Check if the point lies on the straight line of this line instance.
     *
     * The first steps involves only addition and subtraction. Only if this test is positive will
     * the second step be calculated, which involves division.
     *
     * @param pt Vector2D point to check
     * @return True if point lies on line.
     */
    @Override
    public boolean isPointInside(Vector2D pt) {
        return isPointWithinAngle(pt) && isPointInLine(pt);
    }

    /**
     * Draw the line onto a canvas using a given GraphicsContext.
     * @param gc {GraphicsContext} Context of the canvas to draw on.
     */
    @Override
    public void draw(GraphicsContext gc) {

        Vector2D pStart = this.getScreenVertex(0);
        Vector2D pEnd = this.getScreenVertex(1);

        gc.setStroke(selected ? selectedColor : color);
        gc.setLineWidth(thickness);
        gc.strokeLine(
                pStart.getValue(0, 0),
                pStart.getValue(0, 1),
                pEnd.getValue(0, 0),
                pEnd.getValue(0, 1)
        );
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setStartCoordinates(double x, double y) {
        Vector2D pStart = this.getLocalVertex(0);
        pStart.setValue(0, 0, x);
        pStart.setValue(0, 1, y);
    }

    public void setEndCoordinates(double x, double y) {
        Vector2D pEnd = this.getLocalVertex(1);
        pEnd.setValue(0, 0, x);
        pEnd.setValue(0, 1, y);
    }

    /**
     * Setter for line color.
     * @param newColor {Color} New line color to be set.
     */
    public void setColor(Color newColor) {
        this.color = newColor;
    }

    /**
     * Setter for line thickness.
     * @param thickness {float} New line thickness (only values > 0)
     */
    public void setThickness(float thickness) {
        if (thickness > 0) {
            this.thickness = thickness;
        }
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

}
