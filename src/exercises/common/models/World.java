package exercises.common.models;

import exercises.common.interfaces.ifScreenProjectionStrategy;
import exercises.common.utils.*;

/**
 * World model to represent a virtual world with its
 * properties.
 */
public class World {

    private TransformationMatrix3D worldTransformMatrix = new TransformationMatrix3D();
    private Vector2D worldScreenCenter = new Vector2D(0, 0);
    private AbstractCamera camera;

    /**
     * Constructor.
     * @param projectionStrategy
     */
    public World(ifScreenProjectionStrategy projectionStrategy) {
        this.worldTransformMatrix.establishIdentityMatrix();
        this.camera = new OrthogonalXYCamera(this);
    }

    private Vector3D applyWorld(Vector3D localVector) {
        Matrix transformed = this.worldTransformMatrix.multiply(localVector, null);
        return new Vector3D(transformed.getValue(0, 0), transformed.getValue(0, 1), transformed.getValue(0, 2));
    }

    /**
     * Since the world is always in 3D, we need to make sure that any vector coming
     * in here is Vector3D. Convert a Vector2D by adding z-coordinate = 1.
     * @param vector {AbstractVector}
     * @return {Vector3D}
     */
    private Vector3D enforce3DVector(AbstractVector vector) {
        return (vector.getNumRows() == 3 ? new Vector3D(vector.getValue(0, 0), vector.getValue(0, 1), 1) : (Vector3D) vector);
    }

    public void setCamera(AbstractCamera camera) {
        this.camera = camera;
    }

    /**
     * Apply this world's global transformation matrix to a local vector to
     * calculate an objects absolute position in the world.
     * @param localVector {AbstractVector} Local vector.
     * @return {Vector3D} Absolute world vector.
     */
    public Vector3D getWorldCoordinates(AbstractVector localVector) {
        return this.applyWorld(
                this.enforce3DVector(localVector)
        );
    }

    /**
     * Main world method to transform the coordinates of a geometric object into
     * world coordinates and then project them to screen coordinates. The current
     * projection is a simple orthogonal projection without any perspective.
     * @param localVector {Vector3D} local geometric vector to be projected.
     * @return {Vector2D} Screen projected vector
     */
    public Vector2D getScreenCoordinates(AbstractVector localVector) {
        return this.camera.projectWorldToScreen(
                this.getWorldCoordinates(localVector)).combine(this.worldScreenCenter);
    }

    /**
     * Global world transformation
     * @param transformationMatrix {TransformationMatrix3D}
     */
    public void transformWorld(TransformationMatrix3D transformationMatrix) {
        this.worldTransformMatrix.multiply(transformationMatrix, this.worldTransformMatrix);
    }

    /**
     * Sets the screen center to a new vector.
     * @param newCenter {Vector2D} new screen center
     */
    public void setScreenCenter(Vector2D newCenter) {
        this.worldScreenCenter = newCenter;
    }
}
