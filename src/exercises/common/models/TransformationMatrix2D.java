package exercises.common.models;

/**
 * 2D Transformation Matrix
 */
public class TransformationMatrix2D extends AbstractTransformationMatrix {

    /**
     * Create an identity matrix
     * @return {TransformMatrix2D} Identity Matrix
     */
    public static TransformationMatrix2D createIdentityMatrix() {
        TransformationMatrix2D matrix = new TransformationMatrix2D();
        matrix.establishIdentityMatrix();
        return matrix;
    }

    /**
     * Create an uniform scaling matrix.
     * @param scale {double} Scale factor
     * @return {TransformMatrix2D} Scaling Matrix
     */
    public static TransformationMatrix2D createUniformScalingMatrix(double scale) {
        return TransformationMatrix2D.createScalingMatrix(scale, scale);
    }

    /**
     * Create a scaling matrix.
     * @param scaleX {double} Scale factor X
     * @param scaleY {double} Scale factor Y
     * @return {TransformMatrix2D} Scaling Matrix
     */
    public static TransformationMatrix2D createScalingMatrix(double scaleX, double scaleY) {
        TransformationMatrix2D matrix = new TransformationMatrix2D();
        matrix.setValues(
                scaleX,      0, 0,
                     0, scaleY, 0,
                     0,      0, 1
        );
        return matrix;
    }

    /**
     * Create a rotation matrix.
     * @param degrees {double} Rotation in degrees
     * @return {TransformMatrix2D} Rotation matrix
     */
    public static TransformationMatrix2D createRotationMatrix(double degrees) {

        double radians = Math.PI / 180 * degrees;

        TransformationMatrix2D matrix = new TransformationMatrix2D();
        matrix.setValues(
                Math.cos(radians), -Math.sin(radians), 0,
                Math.sin(radians), Math.cos(radians),  0,
                0,                 0,  1
        );

        return matrix;
    }

    /**
     * Create a translation matrix.
     * @param dx {double} Translation X
     * @param dy {double} Translation Y
     * @return {TransformMatrix2D} Translation matrix
     */
    public static TransformationMatrix2D createTranslationMatrix(double dx, double dy) {
        TransformationMatrix2D matrix = new TransformationMatrix2D();
        matrix.setValues(
                1, 0, dx,
                0, 1, dy,
                0, 0, 1
        );
        return matrix;
    }

    /**
     * Constructor. Initialize matrix cells.
     * */
    public TransformationMatrix2D() {
        super(3, 3);
        this.establishIdentityMatrix();
    }
}
