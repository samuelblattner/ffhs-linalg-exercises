package application.interfaces;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.swing.text.View;

/**
 * Created by samuelblattner on 28.08.17.
 */
public interface ifExercise {

    public String getExerciseName();
    public String getExerciseDescription();
    public void load(BorderPane container);
}
