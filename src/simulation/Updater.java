package simulation;

public class Updater implements GridManipulator
{
    public void apply(Grid grid, Vector2 pos)
    {
        grid.at(pos).update();
    }
}
