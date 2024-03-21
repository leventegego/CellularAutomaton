package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.*;
import java.util.*;
import simulation.*;

public class VectorTest
{   
    Vector2 a, b, c, d, e;

    @Before
    public void setup()
    {
        a = new Vector2(1, 2);
        b = new Vector2(3, 4);

        c = new Vector2(4, 6);      // a + b
        d = new Vector2(-2, -2);    // a - b
        e = new Vector2(-1, -2);    // -a

    }
    
    @Test
    public void AlgebraTest()
    {
        assertEquals(c, Vector2.sum(a, b));
        assertEquals(d, Vector2.diff(a, b));
        assertEquals(e, Vector2.opposite(a));
    }
    
    @Test
    public void StringTest()
    {
        assertEquals("(1, 2)", a.toString());
        assertEquals("(3, 4)", b.toString());
        assertEquals("(4, 6)", c.toString());
        assertEquals("(-2, -2)", d.toString());
        assertEquals("(-1, -2)", e.toString());
    }

    @Test
    public void HashTest()
    {

        assertNotEquals(a.hashCode(), b.hashCode());
        assertNotEquals(b.hashCode(), c.hashCode());
        assertNotEquals(c.hashCode(), d.hashCode());
        
        
        
        // A set is a hashCode-ot hasznalja,
        
        Set<Vector2> set1 = new HashSet<Vector2>
        ( Arrays.asList(a, b, c, d) );

        Set<Vector2> set2 = new HashSet<Vector2>
        ( Arrays.asList(d, c, b, a) );

        Set<Vector2> set3 = new HashSet<Vector2>
        ( Arrays.asList(d, a, c, b) );

        Set<Vector2> set4 = new HashSet<Vector2>
        ( Arrays.asList(b, c, d, a) );


        assertEquals(set1, set2);
        assertEquals(set1, set3);
        assertEquals(set1, set4);
    }
}
