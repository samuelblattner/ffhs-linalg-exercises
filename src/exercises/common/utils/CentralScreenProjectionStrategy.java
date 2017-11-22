package exercises.common.utils;

import exercises.common.interfaces.ifScreenProjectionStrategy;

/**
 * Created by samuelblattner on 30.10.17.
 */
public class CentralScreenProjectionStrategy implements ifScreenProjectionStrategy {

    private static int CAMERA_Z = 10;
    private static double PERSPECTIVE = 0.1;

    @Override
    public Vector2D projectWorldToScreen(Vector3D worldVector) {
        double distToCamera = worldVector.getValue(0, 2) + CAMERA_Z;
        double z_factor = 100 / worldVector.getValue(0, 2);
        System.out.println(distToCamera);
        System.out.println(z_factor);
        return new Vector2D(worldVector.getValue(0, 0) * z_factor, worldVector.getValue(0, 1) * z_factor);
    }
}
