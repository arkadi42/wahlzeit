package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValueObjectTest {
    @Test
    public void testSize1(){
        Coordinate c3 = CartesianCoordinate.getCartesianCoordinate(5,12,13);
        Coordinate c4 = SphericCoordinate.getSphericalCoordinate( 1.176005207, 0.7853981634, 18.38477631);
        assertEquals(SphericCoordinate.allCoordinates.size(), 1);
    }
    @Test
    public void testSize3(){
        Coordinate c3 = CartesianCoordinate.getCartesianCoordinate(50,12,13);
        Coordinate c4 = SphericCoordinate.getSphericalCoordinate( 0.176005207, 0.7853981634, 18.38477631);
        assertEquals(SphericCoordinate.allCoordinates.size(), 3);
    }
}
