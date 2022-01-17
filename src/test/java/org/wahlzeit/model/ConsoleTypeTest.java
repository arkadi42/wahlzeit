package org.wahlzeit.model;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for the ConsoleType class
 */
public class ConsoleTypeTest {
    @Test
    public void testSubType(){
        ConsoleType t1 = new ConsoleType("Playstation");
        ConsoleType t11 = new ConsoleType("Playstation1");
        ConsoleType t12 = new ConsoleType("Playstation2");
        ConsoleType t13 = new ConsoleType("Playstation3");
        ConsoleType t14 = new ConsoleType("Playstation4");
        ConsoleType t15 = new ConsoleType("Playstation5");
        ConsoleType t16 = new ConsoleType("Playstation6");

        ConsoleType t111 = new ConsoleType("Playstation1.1");
        ConsoleType t112 = new ConsoleType("Playstation1.2");
        ConsoleType t113 = new ConsoleType("Playstation1.3");

        ConsoleType t1111 = new ConsoleType("Playstation1.1.1");

        ConsoleType t2 = new ConsoleType("XBOX");

        t11.addSubType(t111);
        t11.addSubType(t112);
        t11.addSubType(t113);

        t1.addSubType(t11);
        t1.addSubType(t12);
        t1.addSubType(t13);
        t1.addSubType(t14);
        t1.addSubType(t15);
        t1.addSubType(t16);

        t111.addSubType(t1111);

        assertTrue(t1.isSubType(t11));
        assertTrue(t1.isSubType(t12));
        assertTrue(t1.isSubType(t13));
        assertTrue(t1.isSubType(t14));
        assertTrue(t1.isSubType(t15));
        assertTrue(t1.isSubType(t16));


        assertTrue(t1.isSubType(t111));

        assertTrue(t1.isSubType(t1111));

        assertTrue(!t1.isSubType(t2));
    }
}
