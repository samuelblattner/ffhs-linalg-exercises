package exercises.common;

import application.interfaces.ifExercise;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * Created by samuelblattner on 28.08.17.
 */
public abstract class AbstractExercise implements ifExercise {

    // Statics
    private static final String EXERCISE_NAME = "Abstract Exercise";
    private static final String EXERCISE_DESC = "Abstract Exercise Description";

    // State
    private boolean isLoaded = false;

    // References
    protected BorderPane container;

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
    public void load(BorderPane container) {

        if (isLoaded) { return; }

        this.container = container;

        initializeExercise(container);

        isLoaded = true;
    }

    protected void initializeExercise(BorderPane container) {
        onExerciseInitialized();
    }

    protected void onExerciseInitialized() {

    }
}
