package exercises.common.models;

import exercises.common.utils.Vector2D;
import exercises.common.utils.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Line model representing a straight line defined by two vectors ``pStart`` and ``pEnd``.
 * Implements ifCanvasDrawable and is able to draw itself onto a Canvas, given its
 * GraphicContext.
 * Implements basic methods to verify if a given point lies on the line within
 * a specific tolerance.
 */
public class Axis3D extends AbstractGeometry3D implements ifCanvasDrawable {

    /**
     * Constructor.
     */
    public Axis3D(double length, World world) {
        super(world);
        Vector3D[] vertices = {
                new Vector3D(-0.05, -length/2, 0),
                new Vector3D(0.05, -length/2, 0),
                new Vector3D(0.05, length/2, 0),
                new Vector3D(-0.05, length/2, 0),

                new Vector3D(0, -length/2, -0.05),
                new Vector3D(0, -length/2, 0.05),
                new Vector3D(0, length/2, 0.05),
                new Vector3D(0, length/2, -0.05),
        };
        this.setVertices(vertices);
    }

    /**
     * Draw the line onto a canvas using a given GraphicsContext.
     *
     * @param gc {GraphicsContext} Context of the canvas to draw on.
     */
    @Override
    public void draw(GraphicsContext gc) {

        Vector2D p1 = this.getScreenVertex(0);
        Vector2D p2 = this.getScreenVertex(1);
        Vector2D p3 = this.getScreenVertex(2);
        Vector2D p4 = this.getScreenVertex(3);

        Vector2D p5 = this.getScreenVertex(4);
        Vector2D p6 = this.getScreenVertex(5);
        Vector2D p7 = this.getScreenVertex(6);
        Vector2D p8 = this.getScreenVertex(7);

        gc.setStroke(selected ? selectedColor : color);
        gc.setLineWidth(thickness);
        gc.setFill(Color.GREEN);
        gc.fillPolygon(new double[]{
                        p1.getValue(0, 0),
                        p2.getValue(0, 0),
                        p3.getValue(0, 0),
                        p4.getValue(0, 0)
                }, new double[]{
                        p1.getValue(0, 1),
                        p2.getValue(0, 1),
                        p3.getValue(0, 1),
                        p4.getValue(0, 1),
                },
                4);
        gc.setFill(Color.RED);

        gc.fillPolygon(new double[]{
                        p5.getValue(0, 0),
                        p6.getValue(0, 0),
                        p7.getValue(0, 0),
                        p8.getValue(0, 0)
                }, new double[]{
                        p5.getValue(0, 1),
                        p6.getValue(0, 1),
                        p7.getValue(0, 1),
                        p8.getValue(0, 1),
                },
                4);
    }

}
