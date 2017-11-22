package exercises.common.models;

import exercises.common.utils.AbstractVector;
import exercises.common.utils.Vector2D;
import exercises.common.utils.Vector3D;

/**
 * Created by samuelblattner on 21.11.17.
 */
public class OrthogonalXYCamera extends AbstractCamera {
    /**
     * Constructor. Takes a reference to a World-instance.
     *
     * @param world {World} World in which this object lives.
     */
    public OrthogonalXYCamera(World world) {
        super(world);
    }

    @Override
    public Vector2D projectWorldToScreen(Vector3D worldVector) {
        return new Vector2D(worldVector.getValue(0, 0), worldVector.getValue(0, 1));
    }
}
