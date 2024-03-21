package test;

import static org.junit.Assert.*;

import org.junit.*;
import simulation.*;



public class GridTest
{

    Grid grid;
    
    @Before
    public void setup()
    {
        grid = new Grid(10);
    }


    @Test
    public void boundsTest()
    {
        assertTrue(grid.isValid(new Vector2(0, 0)));
        assertTrue(grid.isValid(new Vector2(0, 9)));
        assertTrue(grid.isValid(new Vector2(9, 0)));
        assertTrue(grid.isValid(new Vector2(9, 9)));


        assertFalse(grid.isValid(new Vector2(-1, 0)));
        assertFalse(grid.isValid(new Vector2(0, 10)));
    }

    @Test
    public void resizeTest()
    {
        Vector2 pos = new Vector2(9, 9);

        grid.at(pos).setAlive(true);
        
        assertTrue(grid.getSize() == 10);
        assertTrue(grid.at(pos).isAlive());
        grid.resize(11);
        assertTrue(grid.getSize() == 11);
        assertTrue(grid.at(pos).isAlive());
        grid.resize(20);
        assertTrue(grid.getSize() == 20);
        assertTrue(grid.at(pos).isAlive());
        grid.resize(9);
        grid.resize(10);

        assertFalse(grid.at(pos).isAlive());
        
    }

    @Test
    public void forEachTest()
    {
        Vector2 pos1 = new Vector2(0, 0);
        Vector2 pos2 = new Vector2(9, 9);


        grid.at(pos1).setAlive(true);
        grid.at(pos2).setAlive(true);

        grid.forEach(new Clearer());

        assertFalse(grid.at(pos1).isAlive());
        assertFalse(grid.at(pos2).isAlive());



        grid.at(pos1).setNext(true);
        grid.at(pos2).setNext(true);

        assertFalse(grid.at(pos1).isAlive());
        assertFalse(grid.at(pos2).isAlive());

        grid.forEach(new Updater());
        
        assertTrue(grid.at(pos1).isAlive());
        assertTrue(grid.at(pos2).isAlive());


    }
}
