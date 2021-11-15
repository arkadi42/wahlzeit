package org.wahlzeit.model;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.Console;
import java.sql.ResultSet;

/**
 * Test cases for the (Console)Photo class.
 */
public class PhotoTest {

    @Test
    public void testOwnerName(){
        Photo p1 = new ConsolePhoto();
        String s1 = "Hans";
        p1.setOwnerName(s1);
        assert(p1.getOwnerName().equals("Hans"));
    }

    @Test
    public void testPhotoId(){
        ConsolePhoto p2 = new ConsolePhoto(new PhotoId(7662));
        assert(p2.getId().value == 7662);
        return;
    }

    @Test
    public void testConsolePhoto(){
        assertTrue(PhotoFactory.getInstance().createPhoto() instanceof ConsolePhoto);
        return;
    }


}
