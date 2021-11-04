package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for the Coordiante class.
 */
public class CoordinateTest {

    @Test
    public void testConstructors() {
        Coordinate c1 = new Coordinate();
        assert(c1.getX() == 0);
        assert(c1.getY() == 0);
        assert(c1.getZ() == 0);

        Coordinate c2 = new Coordinate(4, -10, 1);
        assert(c2.getX() == 4);
        assert(c2.getY() == -10);
        assert(c2.getZ() == 1);
    }

    @Test
    public void testFunctions(){
        Coordinate c3 = new Coordinate(1,3,-2);
        Coordinate c4 = new Coordinate(-4, 2, 5);

        assertTrue(c3.isEqual(c3));
        assertTrue(!c3.isEqual(c4));
        assertTrue(c3.equals(c3));

        assertEquals(c4.getDistance(c3), 8.660254, 1e-3 );
    }
}
