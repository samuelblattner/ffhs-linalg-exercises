package exercises.common.models;

import exercises.common.utils.Vector2D;
import exercises.common.utils.Vector3D;

/**
 * Base class for 2D-Geometric objects
 */
public abstract class AbstractGeometry2D extends AbstractGeometry<Vector2D, TransformationMatrix2D> {

    /**
     * Constructor. Takes a reference to a World-instance.
     * @param world {World} World in which this object lives.
     */
    public AbstractGeometry2D(World world) {
        super(world, new TransformationMatrix2D());
    }

    /**
     * Get a specific vertex with all the transformations of this object applied.
     * This will use this object's transformationMatrix to perform all local object-related transformations.
     * @param vertexIndex {int} Index of vertex to be returned.
     * @return {Vector2D} Vertex with world coordinates.
     */
    @Override
    public Vector2D getTransformedVertex(int vertexIndex) {
        return (Vector2D) this.transformationMatrix.multiply(this.vertices.get(vertexIndex), Vector2D.createNullVector());
    }

    /**
     * Get a specific vertex with world coordinates. This will apply any
     * world-related transformations and projections.
     * @param vertexIndex {int} Index of vertex to be returned.
     * @return {Vector2D} Vertex with world coordinates.
     */
    @Override
    public Vector2D getWorldVertex(int vertexIndex) {
        Vector3D vector = this.world.getWorldCoordinates(this.getTransformedVertex(vertexIndex));
        return new Vector2D(vector.getValue(0, 0), vector.getValue(0, 1));
    }

    /**
     * Return an instance of this geometric object's initial transformation matrix.
     * @return {TransformationMatrix2D} Initial transformation matrix.
     */
    public TransformationMatrix2D getInitialTransformationMatrix() {
        return TransformationMatrix2D.createIdentityMatrix();
    }
}
