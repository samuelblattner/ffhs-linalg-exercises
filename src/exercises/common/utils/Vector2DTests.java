package exercises.common.utils;

import junit.framework.TestCase;

/**
 * Created by samuelblattner on 28.08.17.
 */
public class Vector2DTests extends TestCase {

    public void testScalarCalculation() {
        Vector2D vector1 = new Vector2D(10, 10);
        Vector2D vector2 = new Vector2D(10, 10);

        assertEquals(200.0, vector1.scalar(vector2));

        vector1 = new Vector2D(10, 10);
        vector2 = new Vector2D(-10, -10);

        assertEquals(-200.0, vector1.scalar(vector2));

        vector1 = new Vector2D(10, 10);
        vector2 = new Vector2D(-10, 10);

        assertEquals(0.0, vector1.scalar(vector2));
    }

    public void testVectorCombination() {

    }

    public void testVectorDifference() {
        Vector2D vector1 = new Vector2D(10, 10);
        Vector2D vector2 = new Vector2D(5, 5);

        assertEquals(10.0, vector1.getValue(0, 0));
        assertEquals(10.0, vector1.getValue(0, 1));
        assertEquals(5.0, vector2.getValue(0, 0));
        assertEquals(5.0, vector2.getValue(0, 1));

        Vector2D result = vector1.difference(vector2);

        assertEquals(5.0, result.getValue(0, 0));
        assertEquals(5.0, result.getValue(0, 1));
    }

    public void testVectorValues() {
        Vector2D vector = new Vector2D(10, 20);

        assertEquals(10.0, vector.getValue(0, 0));
        assertEquals(20.0, vector.getValue(0, 1));
    }

    public void testVectorCloning() {
        Vector2D vectorOriginal = new Vector2D(10, 20);
        Vector2D vectorClone = vectorOriginal.clone();

        assertEquals(10.0, vectorClone.getValue(0, 0));
        assertEquals(20.0, vectorClone.getValue(0, 1));
    }
}
