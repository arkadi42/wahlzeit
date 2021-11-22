package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SphericCoordinateTest {
    @Test
    public void testDistance(){
        CartesianCoordinate c3 = new CartesianCoordinate(5,12,13);
        SphericCoordinate c4 = new SphericCoordinate( 1.176005207, 0.7853981634, 18.38477631);
        SphericCoordinate c5 = c3.asSphericCoordinate();

        assertTrue(c3.isEqual(c3));
        assertTrue(c3.isEqual(c4));
        assertTrue(c3.equals(c3));

        assertEquals(c4.getCartesianDistance(c3), 0, 1e-3 );
        assertEquals(c5.asCartesianCoordinate().getX(), 5, 1e-3 );

        assertEquals(c4.getCentralAngle(c3), 0, 1e-3);
    }

    @Test
    public void testEdgeCaseDistance(){
        SphericCoordinate c1 = new SphericCoordinate(0, 0, -10);
        CartesianCoordinate c2 = c1.asCartesianCoordinate();

        Coordinate c3 = new CartesianCoordinate(0,0,1);
        Coordinate c4 = c3.asSphericCoordinate();

        assertTrue(c3.isEqual(c4));
        assertTrue(c1.isEqual(c2));

        assertEquals(c3.getCartesianDistance(c4), 0, 1e-3);
        assertEquals(c1.getCentralAngle(c2), 0, 1e-3);
    }
}
