package exercises.common;

import exercises.common.models.ifCanvasDrawable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;


/**
 * Abstract Boilerplate for Exercises using JavaFX Canvas
 */
public abstract class AbstractCanvasExercise extends AbstractExercise {

    // References
    protected GraphicsContext gc;
    protected ArrayList<ifCanvasDrawable> drawables;
    private Canvas canvas;

    // Properties
    protected double screenWidth = 0, screenHeight = 0;

    /**
     * Constructor.
     */
    public AbstractCanvasExercise() {
        drawables = new ArrayList<ifCanvasDrawable>();
    }

    /**
     * Initializes an new Canvas and adds it to the parent container's
     * BorderLayout, center position.
     *
     * @param container BorderPane container to be the parent for the Canvas
     */
    private void initializeCanvas(BorderPane container) {
        canvas = new Canvas(container.getWidth(), container.getHeight());
        gc = canvas.getGraphicsContext2D();
        container.setCenter(canvas);

        screenWidth = canvas.getWidth();
        screenHeight = canvas.getHeight();
    }

    /**
     * Called when the Exercise is initialized. Needs to be overridden
     * in order to call the Canvas initialization.

     * @param container Container for the exercise / Canvas
     */
    @Override
    protected void initializeExercise(BorderPane container) {
        initializeCanvas(container);
        super.initializeExercise(container);
    }


    /**
     * Main render method. Clear the canvas and re-draw all graphical elements.
     */
    public void render() {
        gc.clearRect(0, 0, screenWidth, screenHeight);

        for (ifCanvasDrawable drawable: drawables) {
            drawable.draw(gc);
        }
    }

    /**
     * Remove all graphical elements that are in deleted state from the collection.
     */
    public void cleanScene() {
        ArrayList<ifCanvasDrawable> deletables = new ArrayList<ifCanvasDrawable>();
        for (ifCanvasDrawable drawable: drawables) {
            if (drawable.isDeleted()) {
                deletables.add(drawable);
            }
        }

        drawables.removeAll(deletables);
    }
}
