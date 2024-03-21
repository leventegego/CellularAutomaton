package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.*;
import simulation.*;

public class RuleTest
{
    Ruleset ruleset;


    @Test
    public void Test1()
    {
        ruleset = new Ruleset("B123/S567");
        
        assertTrue(ruleset.isBorn(1));
        assertTrue(ruleset.isBorn(2));
        assertTrue(ruleset.isBorn(3));

        assertFalse(ruleset.isBorn(4));
        

        assertTrue(ruleset.doesSurvive(5));
        assertTrue(ruleset.doesSurvive(6));
        assertTrue(ruleset.doesSurvive(7));

        assertFalse(ruleset.doesSurvive(4));

    }

    @Test
    public void Test2()
    {
        // hibas formatumu szabalyok
        assertThrows(IllegalArgumentException.class, () -> new Ruleset(""));
        assertThrows(IllegalArgumentException.class, () -> new Ruleset(" "));
        assertThrows(IllegalArgumentException.class, () -> new Ruleset("S1/B2"));
        assertThrows(IllegalArgumentException.class, () -> new Ruleset("123"));
        assertThrows(IllegalArgumentException.class, () -> new Ruleset("ABC"));
        assertThrows(IllegalArgumentException.class, () -> new Ruleset("S12B11"));
        assertThrows(IllegalArgumentException.class, () -> new Ruleset("SS23/B23"));
        assertThrows(IllegalArgumentException.class, () -> new Ruleset("S23/BB23"));
        assertThrows(IllegalArgumentException.class, () -> new Ruleset("X23/Y23"));
        assertThrows(IllegalArgumentException.class, () -> new Ruleset("X23\\B23"));
        assertThrows(IllegalArgumentException.class, () -> new Ruleset("S12 / B23"));
    }
}
