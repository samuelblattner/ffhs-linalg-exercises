package exercises.common.models;

import exercises.common.utils.Vector2D;
import exercises.common.utils.Vector3D;

/**
 * Created by samuelblattner on 21.11.17.
 */
public class FixedPerspectiveCamera extends AbstractCamera {


    /**
     * Constructor. Takes a reference to a World-instance.
     *
     * @param world {World} World in which this object lives.
     */
    public FixedPerspectiveCamera(World world) {
        super(world);
        this.vertices.add(new Vector3D(0, 0, 0));
        this.vertices.add(new Vector3D(0, 0, 1));
    }

    public void setPosition(Vector3D position) {
        this.setTransformationMatrix(
                TransformationMatrix3D.createTranslationMatrix(
                        position.getValue(0, 0),
                        position.getValue(0, 1),
                        position.getValue(0, 2)
                )
        );
    }

    @Override
    public Vector2D projectWorldToScreen(Vector3D objVector) {

        Vector3D objPos = (Vector3D) objVector.subtract(this.getWorldVertex(0), new Vector3D(0, 0,0));

        Vector3D camProjDistance = this.getWorldVertex(1);
        double objZ = objPos.getValue(0, 2);
        double z_factor =  camProjDistance.getValue(0, 2) / ( objZ < 0 ? 1/Math.abs(objZ) : objZ);
        return new Vector2D(objPos.getValue(0, 0) * z_factor, objPos.getValue(0, 1) * z_factor);
    }
}
