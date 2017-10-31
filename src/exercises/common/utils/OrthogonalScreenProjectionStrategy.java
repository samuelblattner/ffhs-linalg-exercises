package exercises.common.utils;

import exercises.common.interfaces.ifScreenProjectionStrategy;

/**
 * Created by samuelblattner on 30.10.17.
 */
public class OrthogonalScreenProjectionStrategy implements ifScreenProjectionStrategy {

    @Override
    public Vector2D projectWorldToScreen(Vector3D worldVector) {
        return new Vector2D(worldVector.getValue(0, 0), worldVector.getValue(0, 1));
    }
}
