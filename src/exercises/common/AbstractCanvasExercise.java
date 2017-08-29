package exercises.common;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Created by samuelblattner on 28.08.17.
 */
public abstract class AbstractCanvasExercise extends AbstractExercise {

    protected GraphicsContext gc;
    private Canvas canvas;

    // Properties
    protected double screenWidth = 0, screenHeight = 0;

    private void initializeCanvas(BorderPane container) {
        canvas = new Canvas(container.getWidth(), container.getHeight());
        gc = canvas.getGraphicsContext2D();
        container.setCenter(canvas);

        screenWidth = canvas.getWidth();
        screenHeight = canvas.getHeight();
    }

    @Override
    protected void initializeExercise(BorderPane container) {
        initializeCanvas(container);

        super.initializeExercise(container);
    }
}
