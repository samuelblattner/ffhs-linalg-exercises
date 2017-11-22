package exercises.exercise3;

import exercises.common.AbstractCanvasExercise;
import exercises.common.models.*;
import exercises.common.utils.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Optional;

/**
 * LinAlg.BSc INF 2015.ZH5-Mo.HS17/18: Exercise 3
 * ----------------------------------------------
 * <p>
 * Implement a JavaFX-Application shows a rotating cube and allow the user to set the orientation of
 * the cube's rotation axis.
 */
public class Exercise3 extends AbstractCanvasExercise {

    // Statics
    // =======
    private static final String EXERCISE_NAME = "Drehender 3D-Würfel";
    private static final String EXERCISE_DESC = "Erstellen Sie eine JavaFX-Applikation, die in einem Fenster eine Animation eines drehenden Würfels zeigt:\n" +
            "In der Ursprungsposition seien die Würfelkanten parallel zur den Koordinateachsen.\n\n" +
            "Der Benutzer kann mit einem drei Textfeldern (oder Slidern) die Richtung der Drehachse spezifizieren.\n";

    private static final String EXERCISE_CONTROLS_PATH = "ui/exercise3-controls.fxml";
    private static final String EXERCISE_TRANSLATION_DIALOG_PATH = "ui/translation-dialog.fxml";
    private static final String EXERCISE_SCALING_DIALOG_PATH = "ui/scaling-dialog.fxml";

    private static final int ANIM_STEP_INTERVAL_MS = 10;

    private World world = new World(new CentralScreenProjectionStrategy());

    // Transformation
    // ==============
    private TransformationMatrix3D userTransformationMatrix;
    MatrixTableViewController matrixController;
    Axis3D rotationAxis;
    FixedPerspectiveCamera cam;

    // Animation
    // =========
    private AnimationTimer animThread;
    private boolean isAnimating = false;

    // GUI
    // ===
    @FXML
    private TableView matrixTable;

    @FXML
    private Button btTransform, btRotation, btScaling, btTranslation, btReset, btAnimation;

    @FXML
    private Slider slAxisX, slAxisY, slAxisZ;

    @FXML
    private TextField tfAxisX, tfAxisY, tfAxisZ;

    /**
     * Create all event listeners for this exercise
     */
    private void establishEventListeners() {

        /*
        When the transform button is clicked, the current user Transformation matrix
        should be applied to all objects.
         */
        this.btTransform.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                applyTransformation();
            }
        });

        /*
        When the reset button is clicked, the user Transformation matrix should
        be reset to a unit matrix.
         */
        this.btReset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resetUserTransformation();
            }
        });

        /*
         Rotations
         */
        this.btRotation.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                performUserRotation();
            }
        });

        /*
        Translations
         */
        this.btTranslation.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                performUserTranslation();
            }
        });

        /*
        Scalings
         */
        this.btScaling.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                performUserScaling();
            }
        });

        /*
        Animation
         */
        this.btAnimation.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleAnimation();
            }
        });

        /*
         * User sets x-rotation of cube's rotation axis
         */
        this.slAxisX.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tfAxisX.setText(Double.toString(slAxisX.getValue()));
                updateRotationAxisRotation();
            }
        });

        /*
         * User sets y-rotation of cube's rotation axis
         */
        this.slAxisY.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tfAxisY.setText(Double.toString(slAxisY.getValue()));
                updateRotationAxisRotation();
            }
        });

        /*
         * User sets z-rotation of cube's rotation axis
         */
        this.slAxisZ.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tfAxisZ.setText(Double.toString(slAxisZ.getValue()));
                updateRotationAxisRotation();
            }
        });
    }

    /**
     * Setup method when animation starts.
     */
    private void handleAnimationStarted() {
        this.isAnimating = true;
        this.btAnimation.setText("II");
    }

    /**
     * Tear-down method when animation stopped.
     */
    private void handleAnimationStopped() {
        this.animThread = null;
        this.isAnimating = false;
        this.btAnimation.setText("▶");
        this.render();
    }


    /**
     * Toggles the animation of the cube. Uses the current user transformation
     * matrix and transforms it based on the orientation of the rotation axis.
     */
    private void toggleAnimation() {

        if (this.isAnimating) {
            this.animThread.stop();
            this.handleAnimationStopped();
        } else {
            this.handleAnimationStarted();
            final Exercise3 that = this;
            this.animThread = new AnimationTimer() {

                @Override
                public void handle(long now) {
                    that.applyTransformation();
                        try {
                            Thread.sleep(ANIM_STEP_INTERVAL_MS);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                }
            };
            this.animThread.start();
        }
    }

    /**
     * Reads the sliders values and updates the transformation matrix
     * of the rotation axis. This is nothing more than generating
     * the rotation matrices for each axis and then multiply them.
     */
    private void updateRotationAxisRotation() {

        TransformationMatrix3D axisRotation =
                (TransformationMatrix3D) TransformationMatrix3D.createRotationMatrixX(slAxisX.getValue())
                .multiply(
                        TransformationMatrix3D.createRotationMatrixY(slAxisY.getValue()),
                        TransformationMatrix3D.createIdentityMatrix()
                )
                .multiply(
                        TransformationMatrix3D.createRotationMatrixZ(slAxisZ.getValue()),
                        TransformationMatrix3D.createIdentityMatrix()
                );

        this.rotationAxis.setTransformationMatrix(axisRotation);
        this.render();
    }

    /**
     * Presents a user dialog and asks the user to enter an amount
     * in degrees to perform a rotation.
     */
    private void performUserRotation() {

        TextInputDialog dialog = new TextInputDialog("180");
        dialog.setTitle("Transformation");
        dialog.setHeaderText("Rotation");
        dialog.setContentText("Bitte Anzahl Grad eingeben:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            userTransformationMatrix = TransformationMatrix3D.createRotationMatrixY(Double.valueOf(result.get()));
            setUserTransformation(userTransformationMatrix);
        }
    }

    /**
     * Presents a user dialog for the user to enter dX and dY translation values and
     * then performs the actual translation.
     */
    private void performUserTranslation() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(EXERCISE_TRANSLATION_DIALOG_PATH));
        GridPane dialogContent;
        TextField txtDX;
        TextField txtDY;

        try {
            loader.setController(this);
            dialogContent = loader.load();
            txtDX = (TextField) dialogContent.lookup("#txtDX");
            txtDY = (TextField) dialogContent.lookup("#txtDY");
        } catch (IOException e) {
            System.err.format("Unable to load Dialog Interface for Exercise 2 (%s).", EXERCISE_TRANSLATION_DIALOG_PATH);
            return;
        }

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(dialogContent);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(txtDX.getText(), txtDY.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(translationCoordinates -> {

            double dx = Double.valueOf(translationCoordinates.getKey());
            double dy = Double.valueOf(translationCoordinates.getValue());

            userTransformationMatrix = TransformationMatrix3D.createTranslationMatrix(dx, dy, 0);
            setUserTransformation(userTransformationMatrix);
        });
    }

    /**
     * Asks the user the enter sX and sY scaling factors and then performs the actual scaling.
     * #TODO: Make dialog factory class
     */
    private void performUserScaling() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(EXERCISE_SCALING_DIALOG_PATH));
        GridPane dialogContent;
        TextField txtSX;
        TextField txtSY;

        try {
            loader.setController(this);
            dialogContent = loader.load();
            txtSX = (TextField) dialogContent.lookup("#txtSX");
            txtSY = (TextField) dialogContent.lookup("#txtSY");
        } catch (IOException e) {
            System.err.format("Unable to load Dialog Interface for Exercise 2 (%s).", EXERCISE_SCALING_DIALOG_PATH);
            return;
        }

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(dialogContent);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(txtSX.getText(), txtSY.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(translationCoordinates -> {

            double sx = Double.valueOf(translationCoordinates.getKey());
            double sy = Double.valueOf(translationCoordinates.getValue());

            userTransformationMatrix = TransformationMatrix3D.createScalingMatrix(sx, sy, 1);
            setUserTransformation(userTransformationMatrix);
        });
    }

    /**
     * Resets the user transformation matrix to an identity matrix.
     */
    private void resetUserTransformation() {
        this.slAxisX.setValue(0);
        this.slAxisY.setValue(0);
        this.slAxisZ.setValue(0);
        this.updateRotationAxisRotation();
        if (this.isAnimating) {
            this.toggleAnimation();
        }
        for (ifCanvasDrawable geom : drawables) {
            ((AbstractGeometry3D) geom).resetTransformation();
        }
        cam.setPosition(new Vector3D(0, 0, -5));
        this.setUserTransformation(TransformationMatrix3D.createIdentityMatrix());
        this.render();
    }

    /**
     * Sets the user transformation matrix.
     *
     * @param userTransformationMatrix {TransformationMatrix2D}
     */
    private void setUserTransformation(TransformationMatrix3D userTransformationMatrix) {
        this.userTransformationMatrix = userTransformationMatrix;
        this.matrixController.setMatrix(this.userTransformationMatrix);
        this.matrixController.refreshTable();
    }

    /**
     * Performs a transformation of all the objects using the user transformation matrix.
     */
    private void applyTransformation() {

        TransformationMatrix3D raMatrix = this.rotationAxis.getTransformationMatrix();
        TransformationMatrix3D finalTransformation = (TransformationMatrix3D) raMatrix
                .multiply(userTransformationMatrix, TransformationMatrix3D.createIdentityMatrix())
                .multiply(raMatrix.getTransposed(), TransformationMatrix3D.createIdentityMatrix());


        for (ifCanvasDrawable geom : drawables) {
            ((AbstractGeometry3D) geom).transform(finalTransformation);
        }
        render();
    }

    /**
     * Build the additional GUI elements for this exercise.
     */
    private void buildGUI() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(EXERCISE_CONTROLS_PATH));
        try {
            loader.setController(this);
            GridPane exerciseLayout;
            exerciseLayout = loader.load();
            container.setBottom(exerciseLayout);
            this.matrixTable = (TableView) exerciseLayout.lookup("#matrixTable");
            this.btTransform = (Button) exerciseLayout.lookup("#btTransform");
            this.btRotation = (Button) exerciseLayout.lookup("#btRotation");
            this.btScaling = (Button) exerciseLayout.lookup("#btScaling");
            this.btTranslation = (Button) exerciseLayout.lookup("#btTranslation");
            this.btReset = (Button) exerciseLayout.lookup("#btReset");
        } catch (IOException e) {
            System.err.format("Unable to load Controls Interface for Exercise 1 (%s).", EXERCISE_CONTROLS_PATH);
        }
    }

    /**
     * Initializes the world geometry. All we do here is set the screen center of the World
     * to the center of the JavaFX Window, and apply a scaling of 100x, so that you can actually
     * see something ;-). The scaling will lead to 1px corresponding to 100px.
     * Also, we mirror the whole world at the XZ-Plane because the Y-Coordinates are inverted on
     * the screen. This will result in the Y-Coordinate to naturally grow from bottom to top.
     */
    private void setUpWorld() {
        this.world.setScreenCenter(new Vector2D(this.screenWidth / 2, this.screenHeight / 2 + 100));
        this.world.transformWorld((TransformationMatrix3D)
                TransformationMatrix3D.createMirrorXZMatrix().multiply(
                        TransformationMatrix3D.createUniformScalingMatrix(100), TransformationMatrix3D.createIdentityMatrix()
                )
        );
        cam = new FixedPerspectiveCamera(this.world);
        cam.lock();
        cam.setPosition(new Vector3D(0, 0, -5));
        this.world.setCamera(cam);
        this.drawables.add(cam);
    }

    /**
     * Render the graphic elements relevant for this exercise.
     * 1. Rotation axis
     * 2. Cube
     */
    private void buildScene() {

        // Initialize the world
        this.setUpWorld();

        // Initialize the user transformation matrix
        this.userTransformationMatrix = TransformationMatrix3D.createIdentityMatrix();
        this.matrixController = new MatrixTableViewController(this.matrixTable, this.userTransformationMatrix);

        // Rotation Axis
        this.rotationAxis = new Axis3D(20, world);
        this.rotationAxis.setColor(Color.rgb(180, 220, 127));
        this.rotationAxis.lock();
        this.drawables.add(this.rotationAxis);

        // Build Cube
        Cube cube = new Cube(0, 0, 0, 3, this.world);
        this.drawables.add(cube);

        this.setUserTransformation(TransformationMatrix3D.createRotationMatrixY(1));
    }

    // ================ AbstractCanvasExercise Methods ======================
    @Override
    public String getExerciseName() {
        return Exercise3.EXERCISE_NAME;
    }

    @Override
    public String getExerciseDescription() {
        return Exercise3.EXERCISE_DESC;
    }

    /**
     * Setup the exercise.
     */
    @Override
    public void onExerciseInitialized() {
        this.buildGUI();
        this.establishEventListeners();
        this.buildScene();
        render();
        this.toggleAnimation();
    }
}
