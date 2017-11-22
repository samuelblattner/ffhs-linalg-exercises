package exercises.common.models;

/**
 * Created by samuelblattner on 31.10.17.
 */
public class TransformationMatrix3D extends AbstractTransformationMatrix {

    /**
     *
     * @param scale
     * @return
     */
    public static TransformationMatrix3D createUniformScalingMatrix(float scale) {
        TransformationMatrix3D matrix = new TransformationMatrix3D();
        matrix.establishUniformScalingMatrix(scale);
        return matrix;
    }

    public static TransformationMatrix3D createIdentityMatrix() {
        TransformationMatrix3D matrix = new TransformationMatrix3D();
        matrix.establishIdentityMatrix();
        return matrix;
    }

    public static TransformationMatrix3D createMirrorXZMatrix() {
        TransformationMatrix3D matrix = new TransformationMatrix3D();

        matrix.setValues(
                1,  0, 0, 0,
                0, -1, 0, 0,
                0,  0, 1, 0,
                0,  0, 0, 1
        );

        return matrix;
    }

    /**
     * Constructor. Initialize matrix cells.
     *
     * */
    public TransformationMatrix3D() {
        super(4, 4);
        this.establishIdentityMatrix();
    }

    /**
     * Create a rotation matrix.
     * @param degrees {double} Rotation in degrees
     * @return {TransformMatrix2D} Rotation matrix
     */
    public static TransformationMatrix3D createRotationMatrixX(double degrees) {

        double radians = Math.PI / 180 * degrees;

        TransformationMatrix3D matrix = new TransformationMatrix3D();
        matrix.setValues(
                1,              0,                 0,     0,
                0, Math.cos(radians), -Math.sin(radians), 0,
                0, Math.sin(radians), Math.cos(radians),  0,
                0,                  0,                 0, 1
        );

        return matrix;
    }

    /**
     * Create a rotation matrix.
     * @param degrees {double} Rotation in degrees
     * @return {TransformMatrix2D} Rotation matrix
     */
    public static TransformationMatrix3D createRotationMatrixY(double degrees) {

        double radians = Math.PI / 180 * degrees;

        TransformationMatrix3D matrix = new TransformationMatrix3D();
        matrix.setValues(
                Math.cos(radians) , 0, Math.sin(radians), 0,
                                 0, 1,                 0, 0,
                -Math.sin(radians), 0, Math.cos(radians), 0,
                0,                  0,                 0, 1
        );

        return matrix;
    }

    /**
     * Create a rotation matrix.
     * @param degrees {double} Rotation in degrees
     * @return {TransformMatrix2D} Rotation matrix
     */
    public static TransformationMatrix3D createRotationMatrixZ(double degrees) {

        double radians = Math.PI / 180 * degrees;

        TransformationMatrix3D matrix = new TransformationMatrix3D();
        matrix.setValues(
                Math.cos(radians), -Math.sin(radians), 0, 0,
                Math.sin(radians), Math.cos(radians),  0, 0,
                0,                  0,                 1, 0,
                0,                  0,                 0, 1
        );

        return matrix;
    }


    /**
     * Create a translation matrix.
     * @param dx {double} Translation X
     * @param dy {double} Translation Y
     * @return {TransformMatrix2D} Translation matrix
     */
    public static TransformationMatrix3D createTranslationMatrix(double dx, double dy, double dz) {
        TransformationMatrix3D matrix = new TransformationMatrix3D();
        matrix.setValues(
                1, 0, 0, dx,
                0, 1, 0, dy,
                0, 0, 1, dz,
                0, 0, 0,  1
        );
        return matrix;
    }


    /**
     * Create a scaling matrix.
     * @param scaleX {double} Scale factor X
     * @param scaleY {double} Scale factor Y
     * @return {TransformMatrix2D} Scaling Matrix
     */
    public static TransformationMatrix3D createScalingMatrix(double scaleX, double scaleY, double scaleZ) {
        TransformationMatrix3D matrix = new TransformationMatrix3D();
        matrix.setValues(
                scaleX, 0, 0, 0,
                0, scaleY, 0, 0,
                0,      0, scaleZ, 0,
                0,      0, 0, 1
        );
        return matrix;
    }
}
