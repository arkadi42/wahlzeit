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

        assertTrue(c5.equals(c4));
        assertTrue(c3.isEqual(c4));
        assertTrue(c3.isEqual(c5));

        assertEquals(c4.getCartesianDistance(c3), 0, 1e-3 );
        assertEquals(c4.asCartesianCoordinate().getX(), 5, 1e-3 );

        assertEquals(c3.getCartesianDistance(c4), 0, 1e-3);
    }

    @Test
    public void testAngle(){
        SphericCoordinate c1 = new SphericCoordinate(0.5, 0.5, -10);
        CartesianCoordinate c2 = c1.asCartesianCoordinate();

        Coordinate c3 = new CartesianCoordinate(10,5,1);
        Coordinate c4 = c3.asSphericCoordinate();

        assertTrue(c3.isEqual(c4));
        assertTrue(c1.isEqual(c2));

        assertEquals(c3.getCentralAngle(c1), 0.86243, 1e-3);
        assertEquals(c2.getCentralAngle(c4), 0.86243, 1e-3);

    }

    @Test
    public void testConversion(){
        CartesianCoordinate cc1 = new CartesianCoordinate(-5, 20, 2);

        assertEquals(cc1.asSphericCoordinate().getRadius(), 20.7123, 1e-3);
        assertEquals(cc1.asSphericCoordinate().getPhi(), 1.8157, 1e-3);
        assertEquals(cc1.asSphericCoordinate().getTheta(), 1.4740, 1e-3);

        SphericCoordinate sp1 = new SphericCoordinate(1.8157, 1.4740, 20.7123);

        assertEquals(sp1.asCartesianCoordinate().getX(), -5, 1e-2);
        assertEquals(sp1.asCartesianCoordinate().getY(), 20, 1e-2);
        assertEquals(sp1.asCartesianCoordinate().getZ(), 2, 1e-2);
    }
}
