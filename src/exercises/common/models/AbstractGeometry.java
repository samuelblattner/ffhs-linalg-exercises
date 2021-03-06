package exercises.common.models;

import exercises.common.utils.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract geometry class that handles the basics for transformation. Every object inheriting
 * from this class specifies a given number of vertices used to define the shape of the object.
 * These vertices are represented by vectors and are all automatically transformed with the
 * transform-method.
 *
 * Each object is assigned a World-instance that defines further details about the 'world'
 * the object resides in.
 *
 * The locked property defines if the object is affected by transformations or if it stays in place.
 *
 * Every object has its own transformationMatrix that defines how the object should be transformed in its world.
 * Every transformation changes the transformationMatrix.
 *
 */
public abstract class AbstractGeometry<V extends AbstractVector, M extends AbstractTransformationMatrix> implements ifCanvasDrawable {

    // Relations
    protected World world;

    // Properties
    protected List<V> vertices;
    protected float thickness = 1.0f;
    protected Color color = Color.rgb(0, 0, 0);
    protected Color selectedColor = Color.rgb(29, 255, 213);

    // State
    protected boolean governed = false;
    protected boolean deleted = false;
    protected boolean selected = false;

    // State
    private boolean locked = false;

    // Transformation
    protected M transformationMatrix;

    /**
     * Constructor. Takes a reference to a World-instance.
     * @param world {World} World in which this object lives.
     */
    public AbstractGeometry(World world, M transformationMatrix) {
        this.world = world;
        this.vertices = new ArrayList<V>();
        this.transformationMatrix = transformationMatrix;
    }

    /**
     * Set the value of a given vertex of this geometry.
     * @param vertexIndex {int} Index of vertex to update
     * @param vector {AbstractVector} Vector with which to update vertex
     */
    public void setVertex(int vertexIndex, V vector) {
        if (vertexIndex > 0 && vertexIndex < this.vertices.size()) {
            V vertex = this.vertices.get(vertexIndex);
            for (int r = 0; r < vertex.getNumRows(); r++) {
                vertex.setValue(0, r, vector.getValue(0, r));
            }
        }
    }

    /**
     * Set this object's vertices.
     * @param vertices {AbstractVector[]} Vertices to define this object.
     */
    public void setVertices(V[] vertices) {
        for (V vertex : vertices) {
            this.vertices.add(vertex);
        }
    }

    /**
     * Lock this object to ignore future transformations.
     */
    public void lock() {
        this.locked = true;
    }

    /**
     * Unlock this object to be affected by future transformations.
     */
    public void unlock() {
        this.locked = false;
    }

    /**
     * Get a specific vertex with object-local coordinates.
     * @param vertexIndex {int} index of vertex to return
     * @return {AbstractVector} Vector of vertex
     */
    public V getLocalVertex(int vertexIndex) {
        return this.vertices.get(vertexIndex);
    }

    /**
     * This will use the projection capabilities of this object's World to
     * calculate the screen coordinates of the specific Vertex.
     * @param vertexIndex {int} Index of vertex to be returned.
     * @return {Vector2D} Screencoordinates of this Vertex.
     */
    public Vector2D getScreenVertex(int vertexIndex) {
        if (this.governed) {
            return this.world.getScreenCoordinates(this.getWorldVertex(vertexIndex));
        } else {
            return this.world.getScreenCoordinates(this.getTransformedVertex(vertexIndex));
        }
    }

    /**
     * Perform a transformation using a matrix.
     * @param newTransformationMatrix {Matrix} transformation matrix to be applied.
     */
    public void transform(Matrix newTransformationMatrix) {
        if (this.locked) {
            return;
        }
//        this.transformationMatrix = (M) newTransformationMatrix.multiply(this.transformationMatrix, this.getInitialTransformationMatrix());
        this.transformationMatrix =(M) this.transformationMatrix.multiply(newTransformationMatrix, this.getInitialTransformationMatrix());
    }

    public void setTransformationMatrix(M transformationMatrix) {
        this.transformationMatrix = (M) transformationMatrix;
    }

    public M getTransformationMatrix() {
        return this.transformationMatrix;
    }

    /**
     * Resets this object's transformation to initial state (no transformation).
     */
    public void resetTransformation() {
        this.transformationMatrix.establishIdentityMatrix();
    }

    /**
     * Getter for deletion state
     * @return {boolean} True if deleted
     */
    @Override
    public boolean isDeleted() {
        return this.deleted;
    }


    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void setThickness(float thickness) {
        if (thickness > 0) {
            this.thickness = thickness;
        }
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean isPointInside(AbstractVector vector) {
        return false;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw(GraphicsContext gc) {}

    public abstract V getTransformedVertex(int vertexIndex);
    public abstract M getInitialTransformationMatrix();
    public abstract V getWorldVertex(int vertexIndex);
}
