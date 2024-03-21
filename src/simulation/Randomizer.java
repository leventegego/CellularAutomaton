package simulation;

import java.util.Random;

public class Randomizer implements GridManipulator
{

    private Random r;

    /**
     * Inicializalja a randomizalot
     */
    public Randomizer ()
    {
        r = new Random();
    }

    /**
     * GridManipulator implementacioja
     * Random beallitja a cella allapotat.
     * 
     * @param grid A tabla, aminek a cellait vizsgaljuk.
     * @param pos A pozicio, ahol az adott cella van a tablaban.
     */
    public void apply(Grid grid, Vector2 pos)
    {
        grid.at(pos).setAlive( r.nextInt(2) == 1 );
    }
    
}
