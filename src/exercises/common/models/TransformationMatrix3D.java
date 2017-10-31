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
}
