package exercises.common.models;

import exercises.common.utils.Vector2D;
import exercises.common.utils.Vector3D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Line model representing a straight line defined by two vectors ``pStart`` and ``pEnd``.
 * Implements ifCanvasDrawable and is able to draw itself onto a Canvas, given its
 * GraphicContext.
 * Implements basic methods to verify if a given point lies on the line within
 * a specific tolerance.
 */
public class LineSegment3D extends AbstractGeometry3D implements ifCanvasDrawable {

    /**
     * Constructor.
     * @param x1 X-Coordinate of start point
     * @param y1 Y-Coordinate of start point
     * @param x2 X-Coordinate of end point
     * @param y2 Y-Coordinate of end point
     */
    public LineSegment3D(double x1, double y1, double z1, double x2, double y2, double z2, World world) {
        super(world);
        Vector3D[] vertices = {
                new Vector3D(x1, y1, z1), new Vector3D(x2, y2, z2)
        };
        this.setVertices(vertices);
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

    public void setStartCoordinates(double x, double y, double z) {
        Vector3D pStart = this.getLocalVertex(0);
        pStart.setValue(0, 0, x);
        pStart.setValue(0, 1, y);
        pStart.setValue(0, 2, z);
    }

    public void setEndCoordinates(double x, double y, double z) {
        Vector3D pEnd = this.getLocalVertex(1);
        pEnd.setValue(0, 0, x);
        pEnd.setValue(0, 1, y);
        pEnd.setValue(0, 2, z);
    }

    public void setVectorsByReference(Vector3D start, Vector3D end) {
        this.vertices.clear();
        this.vertices.add(start);
        this.vertices.add(end);
    }
}
