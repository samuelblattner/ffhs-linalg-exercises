package application;

import application.interfaces.ifExercise;
import exercises.exercise2.Exercise2;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.HashMap;

import exercises.exercise1.Exercise1;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.omg.CORBA.portable.ApplicationException;

public class ApplicationController {

    // Statics
    private static final HashMap<String, Class> EXERCISES = new HashMap<String, Class>();
    private static boolean applicationIsReady = false;
    private static ArrayList<String> loadedExercises = new ArrayList<String>();

    static {
        ApplicationController.EXERCISES.put("EX1", Exercise1.class);
        ApplicationController.EXERCISES.put("EX2", Exercise2.class);
    }

    // References
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

            if (exerciseClass != null && !ApplicationController.loadedExercises.contains(tab.getId())) {

                try {

                    GridPane container = (GridPane) tab.getContent();
                    Label lbTitle = (Label) container.lookup("#lbExerciseName".concat(String.format("%d", tabId)));
                    Label lbDesc = (Label) container.lookup("#lbExerciseDesc".concat(String.format("%d", tabId)));
                    ifExercise exercise = (ifExercise) exerciseClass.newInstance();
                    lbTitle.setText(exercise.getExerciseName());
                    lbDesc.setText(exercise.getExerciseDescription());
                    exercise.load((BorderPane) container.getChildren().get(0));

                    ApplicationController.loadedExercises.add(tab.getId());

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
     * Called when a Tab is selected. Loads the Exercise corresponding to the
     * selected Tab.
     * @param event Select event
     */
    public void onTabSelected(Event event) {
        Tab tab = (Tab) event.getTarget();

        initializeTab(applicationTabPane.getTabs().indexOf(tab));
    }
}
