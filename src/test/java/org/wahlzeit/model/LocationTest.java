package org.wahlzeit.model;

import org.junit.Test;
;

/**
 * Test cases for the Location class.
 */
public class LocationTest {
    @Test
    public void testConstructor(){
        Location l = new Location();
        assert(l.coordinate.getX() == 0);
        assert(l.coordinate.getY() == 0);
        assert(l.coordinate.getZ() == 0);
    }
}
