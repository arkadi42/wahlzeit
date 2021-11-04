package org.wahlzeit.model;
import org.junit.Test;
/**
 * Test cases for the Photo class.
 */
public class PhotoTest {

    @Test
    public void testOwnerName(){
        Photo p1 = new Photo();
        String s = "Hans";
        p1.setOwnerName(s);
        assert(p1.getOwnerName().equals("Hans"));
    }
}
