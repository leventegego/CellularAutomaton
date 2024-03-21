package simulation;

public interface GridManipulator
{
    /**
     * A tabla minden cellara meghivja
     * 
     * @param grid A tabla, aminek a cellait vizsgaljuk.
     * @param pos A pozicio, ahol az adott cella van a tablaban.
     */
    public void apply(Grid grid, Vector2 pos);
}
