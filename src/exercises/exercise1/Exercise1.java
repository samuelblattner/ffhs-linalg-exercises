package exercises.exercise1;

import exercises.common.AbstractCanvasExercise;
import exercises.common.utils.Vector2D;
import exercises.exercise1.models.Line;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.util.ArrayList;


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

    // State
    private boolean isDragging = false;
    private float thickness = 1.0f;

    // References
    private ArrayList<Line> lines = new ArrayList<Line>();
    private Line currentLine;

    private Slider sldTolerance, sldThickness;
    private Label lbTolerance, lbThickness;

    // GUI
    private GridPane exerciseLayout;

    private void buildGUI(BorderPane container) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/exercise1-controls.fxml"));
        try {
            loader.setController(this);
            exerciseLayout = loader.load();
            container.setBottom(exerciseLayout);

            sldTolerance = (Slider) exerciseLayout.lookup("#sldTolerance");
            sldThickness = (Slider) exerciseLayout.lookup("#sldThickness");
            lbTolerance = (Label) exerciseLayout.lookup("#lbTolerance");
            lbThickness = (Label) exerciseLayout.lookup("#lbThickness");

        } catch (IOException e) {

        }
    }

    public void hurrli() {
        System.out.println("burrli");
    }

    /**
     * Create all required event listeners for this exercise.
     */
    private void establishEventListeners() {

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

                    currentLine = new Line(x, y, x, y);
                    currentLine.setThickness(thickness);
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
                    lines.add(currentLine);
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
                    for (Line line: lines) {
                        if (line.isPointInside(new Vector2D(event.getX(), event.getY()))) {
                            line.setSelected(true);
                        } else {
                            line.setSelected(false);
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
                    for (Line line: lines) {
                        if (line.isPointInside(new Vector2D(event.getX(), event.getY()))) {
                            line.setDeleted(true);
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
                for (Line line: lines) {
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
                Line.tolerance = newValue.floatValue();
            }
        });
    }

    /**
     * Remove all lines that are in deleted state from the collection.
     */
    private void cleanScene() {
        ArrayList<Line> deletables = new ArrayList<Line>();
        for (Line line: lines) {
            if (line.isDeleted()) {
                deletables.add(line);
            }
        }

        lines.removeAll(deletables);
    }

    /**
     * Clear the canvas and re-draw all graphical elements.
     * In this case these are just lines.
     */
    private void render() {
        gc.clearRect(0, 0, screenWidth, screenHeight);

        for (Line line: lines) {
            line.draw(gc);
        }
        if (currentLine != null) {
            currentLine.draw(gc);
        }
    }

    // ==================== ifExercise Methods ============================
    @Override
    public String getExerciseName() {
        return EXERCISE_NAME;
    }

    @Override
    public String getExerciseDescription() {
        return EXERCISE_DESC;
    }

    @Override
    protected void onExerciseInitialized() {
        buildGUI(container);
        establishEventListeners();
    }
}
