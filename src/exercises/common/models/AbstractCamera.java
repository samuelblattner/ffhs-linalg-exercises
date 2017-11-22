package exercises.common.models;

import exercises.common.interfaces.ifScreenProjectionStrategy;
import exercises.common.utils.Vector2D;
import exercises.common.utils.Vector3D;

/**
 * Created by samuelblattner on 21.11.17.
 */
public class AbstractCamera extends AbstractGeometry3D implements ifScreenProjectionStrategy {
    /**
     * Constructor. Takes a reference to a World-instance.
     *
     * @param world {World} World in which this object lives.
     */
    public AbstractCamera(World world) {
        super(world);
    }

    @Override
    public Vector2D projectWorldToScreen(Vector3D worldVector) {
        return new Vector2D(0, 0);
    }
}
