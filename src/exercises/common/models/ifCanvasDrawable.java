package exercises.common.models;

import exercises.common.utils.Vector2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Interface defining an object that can be drawn
 * on a JavaFX Canvas.
 */
public interface ifCanvasDrawable {

    void draw(GraphicsContext gc);
    void setSelected(boolean selected);
    boolean isPointInside(Vector2D pt);

}
