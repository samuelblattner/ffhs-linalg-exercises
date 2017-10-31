package exercises.common;

import application.interfaces.ifExercise;
import javafx.scene.layout.BorderPane;


/**
 * Abstract base class for Exercises.
 */
public abstract class AbstractExercise implements ifExercise {

    // State
    private boolean isLoaded = false;

    // References
    protected BorderPane container;

    // ==================== ifExercise Methods ============================
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

    protected void onExerciseInitialized() {}

}
