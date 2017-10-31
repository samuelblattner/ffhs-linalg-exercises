package exercises.common.models;

import exercises.common.utils.Vector3D;

/**
 * Base class for 3D-Geometric objects
 */
public abstract class AbstractGeometry3D extends AbstractGeometry<Vector3D, TransformationMatrix3D> {

    /**
     * Constructor. Takes a reference to a World-instance.
     * @param world {World} World in which this object lives.
     */
    public AbstractGeometry3D(World world) {
        super(world, new TransformationMatrix3D());
    }

    /**
     * Get a specific vertex with all the transformations of this object applied.
     * This will use this object's transformationMatrix to perform all local object-related transformations.
     * @param vertexIndex {int} Index of vertex to be returned.
     * @return {Vector3D} Vertex with world coordinates.
     */
    @Override
    public Vector3D getTransformedVertex(int vertexIndex) {
        return (Vector3D) this.transformationMatrix.multiply(this.vertices.get(vertexIndex), new Vector3D(0, 0, 0));
    }

    /**
     * Get a specific vertex with world coordinates. This will apply any
     * world-related transformations and projections.
     * @param vertexIndex {int} Index of vertex to be returned.
     * @return {Vector3D} Vertex with world coordinates.
     */
    @Override
    public Vector3D getWorldVertex(int vertexIndex) {
        return this.world.getWorldCoordinates(this.getTransformedVertex(vertexIndex));
    }

    /**
     * Return an instance of this geometric object's initial transformation matrix.
     * @return {TransformationMatrix3D} Initial transformation matrix.
     */
    public TransformationMatrix3D getInitialTransformationMatrix() {
        return TransformationMatrix3D.createIdentityMatrix();
    }
}
