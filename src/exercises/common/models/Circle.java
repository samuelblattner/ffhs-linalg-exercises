package exercises.common.models;

import exercises.common.utils.AbstractVector;
import exercises.common.utils.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * Model for a circle
 */
public class Circle extends AbstractGeometry2D implements ifCanvasDrawable {

    /**
     * Constructor. Note, that the radii of this oval are
     * also modelled using vectors. This has the advantage that
     * when applying transformations, the radius is also transformed.
     * @param x X-Coordinate of center
     * @param y Y-Coordinate of center
     */
    public Circle(double x, double y, double radius, World world) {

        super(world);
        Vector2D[] vertices = {
                new Vector2D(x, y),
                new Vector2D(radius, 0),
                new Vector2D(0, radius)
        };
        this.setVertices(vertices);
    }

    // ===================== ifCanvasDrawable Methods =====================
    /**
     * Draw the oval to the graphics context. The radii are calculated by
     * subtracting the center of the oval from both radius-vectors.
     * @param gc GraphicsContext to draw to
     */
    @Override
    public void draw(GraphicsContext gc) {

        double worldRadiusX = this.getWorldVertex(1).difference(this.getWorldVertex(0)).getLength();
        double worldRadiusY = this.getWorldVertex(2).difference(this.getWorldVertex(0)).getLength();

        gc.strokeOval(
                this.getScreenVertex(0).getValue(0, 0) - worldRadiusX,
                this.getScreenVertex(0).getValue(0, 1) - worldRadiusY,
                worldRadiusX * 2,
                worldRadiusY * 2
        );

    }

    @Override
    public void setSelected(boolean selected) {
    }

    @Override
    public boolean isPointInside(AbstractVector pt) {
        return false;
    }

    @Override
    public void setThickness(float thickness) {
    }

    @Override
    public void setDeleted(boolean deleted) {
    }

    @Override
    public boolean isDeleted() {
        return false;
    }

}
