package exercises.common.interfaces;

import exercises.common.utils.Vector2D;
import exercises.common.utils.Vector3D;

/**
 * Interface for Screen Projection Strategies.
 */
public interface ifScreenProjectionStrategy {
    Vector2D projectWorldToScreen(Vector3D worldVector);
}
