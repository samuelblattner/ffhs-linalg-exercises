package exercises.common.models;

import exercises.common.utils.Matrix;
import exercises.common.utils.Vector3D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;


/**
 * Model for a 3D-Cube
 */
public class Cube extends AbstractGeometry3D implements ifCanvasDrawable {

    // Properties
    private List<LineSegment3D> edges;

    /**
     * Constructor. Creates all eight vertices that define the cube based
     * on a center and the size, i.e. the length of one edge.
     *
     *
     *     c4 ----------- c3                  ^ y
     *     |\             |\                  |
     *     | \            | \                 |
     *     |  \           |  \                |
     *     |   c1 ----------- c2              |
     *     |    |         |    |              ----------> x
     *     c8 --|-------- c7   |              \
     *      \   |          \   |               \
     *       \  |           \  |                \
     *        \ |            \ |                 v  -z
     *         c5------------ c6
     *
     * @param x {double} x-Coordinate of center
     * @param y {double} y-Coordinate of center
     * @param z {double} z-Coordinate of center
     * @param size {double} length of an edge
     * @param world {World} world instance to render the individual primitives
     */
    public Cube(double x, double y, double z, double size, World world) {

        super(world);
        this.edges = new ArrayList<>();

        double halfEdge = size / 2.0f;

        // Top square
        Vector3D c1 = new Vector3D(x - halfEdge, y + halfEdge, z - halfEdge);
        Vector3D c2 = new Vector3D(x + halfEdge, y + halfEdge, z - halfEdge);
        Vector3D c3 = new Vector3D(x + halfEdge, y + halfEdge, z + halfEdge);
        Vector3D c4 = new Vector3D(x - halfEdge, y + halfEdge, z + halfEdge);
        this.addEdge(c1, c2, world);
        this.addEdge(c2, c3, world);
        this.addEdge(c3, c4, world);
        this.addEdge(c4, c1, world);

        // Bottom square
        Vector3D c5 = new Vector3D(x - halfEdge, y - halfEdge, z - halfEdge);
        Vector3D c6 = new Vector3D(x + halfEdge, y - halfEdge, z - halfEdge);
        Vector3D c7 = new Vector3D(x + halfEdge, y - halfEdge, z + halfEdge);
        Vector3D c8 = new Vector3D(x - halfEdge, y - halfEdge, z + halfEdge);
        this.addEdge(c5, c6, world);
        this.addEdge(c6, c7, world);
        this.addEdge(c7, c8, world);
        this.addEdge(c8, c5, world);

        // "Pillars"
        this.addEdge(c1, c5, world);
        this.addEdge(c2, c6, world);
        this.addEdge(c3, c7, world);
        this.addEdge(c4, c8, world);
    }

    /**
     * Add a new edge (LineSegment3D) to the cube
     * @param p1 {Vector3D} Start point of the segment.
     * @param p2 {Vector3D} End point of the segment.
     * @param world {World} World instance for the rendering of the segment.
     */
    private void addEdge(Vector3D p1, Vector3D p2, World world) {
        LineSegment3D segment = new LineSegment3D(0L, 0L, 0L, 0, 0, 0, world);
        segment.setVectorsByReference(p1, p2);
        this.edges.add(segment);
    }

    // ===================== ifCanvasDrawable Methods =====================
    /**
     * Draw the cube by iterating over the edges and drawing them.
     * @param gc GraphicsContext to draw to
     */
    @Override
    public void draw(GraphicsContext gc) {
        for (LineSegment3D edge : this.edges) {
            edge.draw(gc);
        }
    }

    /**
     *
     * @param newTransformationMatrix {Matrix} transformation matrix to be applied.
     */
    @Override
    public void transform(Matrix newTransformationMatrix) {
        super.transform(newTransformationMatrix);
        for (LineSegment3D seg : this.edges) {
            seg.setTransformationMatrix(this.transformationMatrix);
        }
    }
}
