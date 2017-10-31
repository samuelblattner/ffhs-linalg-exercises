package exercises.exercise2;

import exercises.common.AbstractCanvasExercise;
import exercises.common.models.*;
import exercises.common.utils.*;
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
 * LinAlg.BSc INF 2015.ZH5-Mo.HS17/18: Exercise 2
 * ----------------------------------------------
 *
 * Implement a JavaFX-Application that allows the user to transform 2D-objects using a transformation matrix.
 */
public class Exercise2 extends AbstractCanvasExercise {

    // Statics
    // =======
    private static final String EXERCISE_NAME = "Affine Transformationen";
    private static final String EXERCISE_DESC = "Schreiben Sie eine JavaFX-Applikation, die die Funktion der zweidimensionalen affinen Abbildungen illustriert.\n" +
            "Der Benutzer sollte dabei eine Abbildungsmatrix einer zweidimensionalen Abbildung eingeben (in einem tabellenarigen Eingabepanel). Die Applikation zeige dann folgendes:\n\n" +
            "1. Parallele zur x-Achse mit einem ganzzahligen Abstand von der x-Achse.\n" +
            "2. Parallele zur y-Achse mit einem ganzzahligen Abstand von der y-Achse.\n" +
            "3. Den Einheitskreis.\n" +
            "4. Ferner soll das Bild der Elemente aus 1-3 unter der angegebenen Abbildungsmatrix eingezeichnet werden.\n";

    private static final String EXERCISE_CONTROLS_PATH = "ui/exercise2-controls.fxml";
    private static final String EXERCISE_TRANSLATION_DIALOG_PATH = "ui/translation-dialog.fxml";
    private static final String EXERCISE_SCALING_DIALOG_PATH = "ui/scaling-dialog.fxml";

    private World world = new World(new OrthogonalScreenProjectionStrategy());

    // Transformation
    // ==============
    private TransformationMatrix2D userTransformationMatrix;
    MatrixTableViewController matrixController;

    // GUI
    // ===
    @FXML
    private TableView matrixTable;

    @FXML
    private Button btTransform, btRotation, btScaling, btTranslation, btReset;

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
        if(result.isPresent()) {
            userTransformationMatrix = TransformationMatrix2D.createRotationMatrix(Double.valueOf(result.get()));
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

            userTransformationMatrix = TransformationMatrix2D.createTranslationMatrix(dx, dy);
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

            userTransformationMatrix = TransformationMatrix2D.createScalingMatrix(sx, sy);
            setUserTransformation(userTransformationMatrix);
        });
    }

    /**
     * Resets the user transformation matrix to an identity matrix.
     */
    private void resetUserTransformation() {
        for (ifCanvasDrawable geom : drawables) {
            ((AbstractGeometry2D) geom).resetTransformation();
        }
        this.setUserTransformation(TransformationMatrix2D.createIdentityMatrix());
    }

    /**
     * Sets the user transformation matrix.
     * @param userTransformationMatrix {TransformationMatrix2D}
     */
    private void setUserTransformation(TransformationMatrix2D userTransformationMatrix) {
        this.userTransformationMatrix = userTransformationMatrix;
        this.matrixController.setMatrix(this.userTransformationMatrix);
        this.matrixController.refreshTable();
        this.applyTransformation();
    }

    /**
     * Performs a transformation of all the objects using the user transformation matrix.
     */
    private void applyTransformation() {
        for (ifCanvasDrawable geom : drawables) {
            ((AbstractGeometry2D) geom).transform(userTransformationMatrix);
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
    }

    /**
     * Render the graphic elements relevant for this exercise.
     * 1. Line Segment parallel to the x-axis
     * 2. Line Segment parallel to the y-axis
     * 3. Unit Circle
     */
    private void buildScene() {

        // Initialize the world
        this.setUpWorld();

        // Initialize the user transformation matrix
        this.userTransformationMatrix = TransformationMatrix2D.createIdentityMatrix();
        this.matrixController = new MatrixTableViewController(this.matrixTable, this.userTransformationMatrix);

        // Build reference system
        LineSegment xAxis = new LineSegment(-2, 0, 2, 0, this.world);
        xAxis.setColor(Color.rgb(0, 128, 128));
        xAxis.lock();
        this.drawables.add(xAxis);

        LineSegment yAxis = new LineSegment(0, -2, 0, 2, this.world);
        yAxis.setColor(Color.rgb(128, 128, 128));
        yAxis.lock();
        this.drawables.add(yAxis);

        // Build transformation geometry
        Circle unitCircle = new Circle(0, 0, 1, this.world);
        this.drawables.add(unitCircle);

        LineSegment parallelX = new LineSegment(1, -2, 1, 2, this.world);
        parallelX.setThickness(5);
        parallelX.setColor(Color.rgb(0, 96, 225));
        this.drawables.add(parallelX);

        LineSegment parallelY = new LineSegment(-2, -1, 2, -1, this.world);
        parallelY.setThickness(5);
        parallelY.setColor(Color.rgb(0, 225, 96));
        this.drawables.add(parallelY);
    }

    // ================ AbstractCanvasExercise Methods ======================
    @Override
    public String getExerciseName() {
        return Exercise2.EXERCISE_NAME;
    }

    @Override
    public String getExerciseDescription() {
        return Exercise2.EXERCISE_DESC;
    }

    @Override
    public void onExerciseInitialized() {
        this.buildGUI();
        this.establishEventListeners();
        this.buildScene();

        render();
    }
}
