package application;

import application.interfaces.ifExercise;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import java.util.HashMap;

import exercises.exercise1.Exercise1;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ApplicationController {

    private static HashMap<String, Class> EXERCISES = new HashMap<String, Class>();
    private static boolean applicationIsReady = false;

    static {
        ApplicationController.EXERCISES.put("EX1", Exercise1.class);
    }

    @FXML
    private TabPane applicationTabPane;

    /**
     * Initializes a specific Tab at index ``tabId`` if it exists. Looks up the Tab id String
     * in the EXERCISES dictionary and loads the Exercise if found.
     * @param tabId Index of the tab to be initialized.
     */
    private void initializeTab(int tabId) {

        if (applicationIsReady && tabId >= 0 && tabId < applicationTabPane.getTabs().size()) {

            Tab tab = applicationTabPane.getTabs().get(tabId);
            Class exerciseClass = ApplicationController.EXERCISES.get(tab.getId());

            if (exerciseClass != null) {

                try {

                    GridPane container = (GridPane) tab.getContent();
                    Label lbTitle = (Label) container.lookup("#lbExerciseName");
                    Label lbDesc = (Label) container.lookup("#lbExerciseDesc");
                    ifExercise exercise = (ifExercise) exerciseClass.newInstance();
                    lbTitle.setText(exercise.getExerciseName());
                    lbDesc.setText(exercise.getExerciseDescription());
                    exercise.load((BorderPane) container.getChildren().get(2));

                } catch (IllegalAccessException e) {
                    System.err.format("Unable to instantiate class %s. Permission denied.", exerciseClass.toString());
                } catch (InstantiationException e) {
                    System.err.format("Unable to instantiate class %s. Error: %s", exerciseClass.toString(), e.toString());
                }
            }
        }
    }

    /**
     * Initializes the first Tab, i.e. the first Exercise upon
     * application launch.
     */
    void initExercises() {
        initializeTab(0);
    }

    void onApplicationReady() {
        applicationIsReady = true;
        initExercises();
    }

    /**
     * Called when a Tab is selected. Loads the Exercise of the tab specified.
     * @param event Select event
     */
    public void onTabSelected(Event event) {
        Tab tab = (Tab) event.getTarget();

        initializeTab(applicationTabPane.getTabs().indexOf(tab));
    }
}
