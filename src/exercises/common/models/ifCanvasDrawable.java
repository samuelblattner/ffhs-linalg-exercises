package exercises.common.models;

import exercises.common.utils.AbstractVector;
import exercises.common.utils.Vector2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Interface defining an object that can be instructed to draw itself
 * on a Canvas using a GraphicsContext.
 * CanvasDrawables also provide functionality to:
 *
 * - be selected and deselected (setSelected)
 * - check if a given point lies within / on the object (isPointInside).
 */
public interface ifCanvasDrawable {

    // Drawing
    void draw(GraphicsContext gc);

    // State management
    void setSelected(boolean selected);
    boolean isPointInside(AbstractVector pt);
    void setThickness(float thickness);
    void setDeleted(boolean deleted);
    boolean isDeleted();
}
