package simulation;

import java.io.*;

public class TextDisplay implements GridDisplay, Serializable
{

    /**
     * GridDisplay interface-t implementalja.
     * Szoveges formaban jeleniti meg a tablat a standard output-on.
     * 
     * @param grid A tabla amit meg kell jeleniteni.
     */
    public void display(Grid grid)
    {
        StringBuilder strb = new StringBuilder();
        for(int row = 0; row < grid.getSize(); row++)
        {
            for(int col = 0; col < grid.getSize(); col++)
                strb.append(cellStr(grid.at(new Vector2(row, col))));
            
            strb.append('\n');
        }

        System.out.println(strb);
    }

    /**
     * Megmondja egy cellarol, hogy milyen
     * string jelkepezi a kirajzolt kepben.
     * 
     * @param c A cella.
     * @return A cella karakteres reprezentacioja.
     */
    public String cellStr(Cell c)
    {
        if(c.isAlive())
            return "[]";
        else
            return "_.";
    }

}
