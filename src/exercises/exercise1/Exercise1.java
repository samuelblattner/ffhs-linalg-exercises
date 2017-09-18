package exercises.exercise1;

import exercises.common.models.ifCanvasDrawable;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

import exercises.common.AbstractCanvasExercise;
import exercises.common.utils.Vector2D;
import exercises.exercise1.models.LineSegment;

/**
 * LinAlg.BSc INF 2015.ZH5-Mo.HS17/18: Exercise 1
 * ----------------------------------------------
 *
 * Implement a simple application allowing the user to draw lines by dragging the mouse and to delete these
 * lines by double-clicking on them.
 */
public class Exercise1 extends AbstractCanvasExercise {

    // Statics
    private static final String EXERCISE_NAME = "«Wohin klickt die Maus?»";
    private static final String EXERCISE_DESC = "Implementieren Sie ein simples Zeichenprogramm mit folgender (minimalen) Funktionalität:\n" +
                                                "- Das Zeichenprogramm kann Strecken zeichnen durch Dragging der Maus.\n" +
                                                "- Durch Doppelklick auf eine bereits gezeichnete Strecke wird diese wieder gelöscht.";

    private static final String EXERCISE_CONTROLS_PATH = "ui/exercise1-controls.fxml";

    // State
    private boolean isDragging = false;
    private float thickness = 1.0f;

    // References
    private LineSegment currentLine;

    private Slider sldTolerance, sldThickness;
    private Label lbTolerance, lbThickness;

    // GUI
    private GridPane exerciseLayout;

    /**
     * Build the control GUI for this Exercise.
     * @param container Container to contain the controls
     */
    private void buildGUI(BorderPane container) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EXERCISE_CONTROLS_PATH));
        try {
            loader.setController(this);
            exerciseLayout = loader.load();
            container.setBottom(exerciseLayout);

            sldTolerance = (Slider) exerciseLayout.lookup("#sldTolerance");
            sldThickness = (Slider) exerciseLayout.lookup("#sldThickness");
            lbTolerance = (Label) exerciseLayout.lookup("#lbTolerance");
            lbThickness = (Label) exerciseLayout.lookup("#lbThickness");

        } catch (IOException e) {
            System.err.format("Unable to load Controls Interface for Exercise 1 (%s).", EXERCISE_CONTROLS_PATH);
        }
    }

    /**
     * Create all required event listeners for this Exercise.
     */
    private void establishEventListeners() {

        // TODO: React to window size changes and adapt canvas

        /*
        When the first dragging event occurs, create a new line. Else, set the end coordinates of the
        currently drawn line to the coordinates of the mouse pointer, then render the scene.
         */
        container.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isDragging) {
                    currentLine.setEndCoordinates(event.getX(), event.getY());
                    render();
                } else {
                    isDragging = true;
                    double x = event.getX();
                    double y = event.getY();

                    currentLine = new LineSegment(x, y, x, y);
                    currentLine.setThickness(thickness);
                    drawables.add(currentLine);
                }
            }
        });

        /*
        Register when the mouse button is released. If in dragging mode, resolve to default mode and add
        the current line to the collection of drawn lines.
         */
        container.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isDragging) {
                    isDragging = false;
                    currentLine = null;
                }
            }
        });

        /*
        When not in dragging mode and the mouse is moved, check with every event if the
        current mouse position lies on any of the lines in the line collection. If this is the
        case, set the line state to 'selected'.
         */
        container.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isDragging) {
                    for (ifCanvasDrawable drawable: drawables) {
                        if (drawable.isPointInside(new Vector2D(event.getX(), event.getY()))) {
                            drawable.setSelected(true);
                        } else {
                            drawable.setSelected(false);
                        }
                    }
                    render();
                }
            }
        });

        /*
        Register mouse double-clicks and mark a line as deleted if the coordinates of the click
        correspond to a position on any of the lines. Call cleanScene afterwards to remove
        all deleted lines from the collection, then render the scene.
         */
        container.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    for (ifCanvasDrawable drawable: drawables) {
                        if (drawable.isPointInside(new Vector2D(event.getX(), event.getY()))) {
                            drawable.setDeleted(true);
                        }
                    }
                    cleanScene();
                    render();
                }
            }
        });

        sldThickness.valueProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lbThickness.setText(String.format("%.0f px", newValue.floatValue()));
                for (ifCanvasDrawable line: drawables) {
                    line.setThickness(newValue.floatValue());
                }
                thickness = newValue.floatValue();
                render();
            }
        });

        sldTolerance.valueProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lbTolerance.setText(String.format("%.0f px", newValue.floatValue()));
                LineSegment.tolerance = newValue.floatValue();
            }
        });
    }

    // ==================== ifExercise Methods ============================
    /**
     * Return Exercise Name / Title
     * @return {String} Exercise Name
     */
    @Override
    public String getExerciseName() {
        return EXERCISE_NAME;
    }

    /**
     * Return Exercise Description.
     * @return {String} Exercise Description
     */
    @Override
    public String getExerciseDescription() {
        return EXERCISE_DESC;
    }

    /**
     * Called when the Exercise has been initialized and is ready
     * for post-initialization routines.
     */
    @Override
    protected void onExerciseInitialized() {
        buildGUI(container);
        establishEventListeners();
    }
}
