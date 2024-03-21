package test;

import static org.junit.Assert.assertTrue;

import java.util.*;
import org.junit.*;
import simulation.*;

public class LifeTest {

    Simulator simulator;    // nem futtatja a szimulaciot csak leptetgeti
    Ruleset ruleset;
    Grid grid;

    
    @Before
    public void setup()
    {
        ruleset = new Ruleset("B3/S23");
        grid = new Grid(10);
        List<GridManipulator> manipulators = new ArrayList<GridManipulator>();
        manipulators.add(ruleset);
        manipulators.add(new Updater());

        // nem kell display, es nem kell msPerTick mert nem futtatunk
        simulator = new Simulator(grid, manipulators, null, 0);
    }

    /**
     * Beallitja a palyat.
     * 
     * @param initial Azok a poziciok ahol elo cellak legyenek.
     */
    private void setGrid(List<Vector2> initial)
    {
        for(int x = 0; x < grid.getSize(); x++)
        {
            for(int y = 0; y < grid.getSize(); y++)
            {
                Vector2 pos = new Vector2(x, y);
                grid.at(pos).setAlive(initial.contains(pos));
            }
        }
    }

    /**
     * Osszehasonlitja a grid-et a megadott vektorokkal.
     * 
     * @param expected Azok a poziciok, ahol elo cellak legyenek.
     */
    private boolean compareResult(List<Vector2> expected)
    {
        for(int x = 0; x < grid.getSize(); x++)
        {
            for(int y = 0; y < grid.getSize(); y++)
            {
                Vector2 pos = new Vector2(x, y);
                if(grid.at(pos).isAlive() != expected.contains(pos))
                    return false;
            }
        }

        return true;
    }

    @Test
    public void blinkerTest()
    {
        // #
        // #     ->    ###
        // #


        List<Vector2> initial = new ArrayList<Vector2>();
        initial.add(new Vector2(1, 1));
        initial.add(new Vector2(1, 2));
        initial.add(new Vector2(1, 3));

        List<Vector2> expectedResult = new ArrayList<Vector2>();
        expectedResult.add(new Vector2(0, 2));
        expectedResult.add(new Vector2(1, 2));
        expectedResult.add(new Vector2(2, 2));


        setGrid(initial);
        simulator.step();
        assertTrue(compareResult(expectedResult));
    }

    @Test
    public void gliderTest()
    {
        //    01234       01234       01234
        //  0   #                          
        //  1    #    ->   # #   ->      # 
        //  2  ###          ##         # # 
        //  3               #           ## 


        List<Vector2> initial = new ArrayList<Vector2>();
        initial.add(new Vector2(2, 1));
        initial.add(new Vector2(3, 2));
        initial.add(new Vector2(1, 3));
        initial.add(new Vector2(2, 3));
        initial.add(new Vector2(3, 3));


        List<Vector2> result1 = new ArrayList<Vector2>();
        result1.add(new Vector2(1, 2));
        result1.add(new Vector2(3, 2));
        result1.add(new Vector2(2, 3));
        result1.add(new Vector2(3, 3));
        result1.add(new Vector2(2, 4));


        List<Vector2> result2 = new ArrayList<Vector2>();
        result2.add(new Vector2(3, 2));
        result2.add(new Vector2(1, 3));
        result2.add(new Vector2(3, 3));
        result2.add(new Vector2(2, 4));
        result2.add(new Vector2(3, 4));


        setGrid(initial);
        simulator.step();
        assertTrue(compareResult(result1));
        simulator.step();
        assertTrue(compareResult(result2));
    }
}
