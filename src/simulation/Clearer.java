package simulation;

public class Clearer implements GridManipulator
{
    /**
     * GridManipulator metodusanak implementacioja.
     * Valogatas nelkul torol minden cellat.
     * 
     * @param grid A tabla, amin a muveletet vegezzuk.
     * @param pos A cella pozicioja a tablaban.
     */
    public void apply(Grid grid, Vector2 pos)
    {
        grid.at(pos).setAlive( false );
    }

}
