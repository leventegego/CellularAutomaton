package simulation;

import java.io.Serializable;
import java.util.*;

public class Grid implements Serializable
{
    private List<List<Cell>> cellMrx;
    private int size;                   // cellMrx negyzetes matrix, ez a merete

    /**
     * Letrehozza a palyat az adott merettel
     * A palya mindig negyzetes
     * 
     * @param size Palya oldalhossza (cellakban)
     */
    public Grid(int size)
    {
        this.size = size;
        cellMrx = createMrx(size);
    }

    /**
     * Letrehoz egy ilyen meretu cella matrixot
     * 
     * @param size_ Matrix merete
     * @return elkeszult matrix
     */
    private List<List<Cell>> createMrx(int size_)
    {
        List<List<Cell>> mrx = new ArrayList<List<Cell>>(size_);
        for(int row = 0; row < size_; row++)
        {
            mrx.add(new ArrayList<Cell>(size_));
            for(int col = 0; col < size_; col++)
                mrx.get(row).add(new Cell());    // uj matrix feltoltese alapertelmezett cellakkal
        }

        return mrx;
    }

    /**
     * 
     * @param p Palya egy (valid) pozicioja
     * @return Cella az adott pozicioban
     */
    public Cell at(Vector2 p)
    {
        return cellMrx.get(p.y()).get(p.x());
    }

    /**
     * 
     * @return Palya oldalhossza (cellakban)
     */
    public int getSize()
    {
        return size;
    }


    /**
     * Ujrameretezi a palyat
     * megorzi azokat a cellakat, amiket meg lehet
     * 
     * @param newSize A palya uj merete
     */
    public void resize(int newSize)
    {
        int oldSize = size;
        newSize = Math.max(0, newSize);                 // 0-nal kisebb erteke nem lehet
        int minSize = Math.min(oldSize, newSize);       // kisebbik meret, a masolashoz kell

        List<List<Cell>> oldMrx = cellMrx;
        List<List<Cell>> newMrx = createMrx(newSize);   // uj matrix alapertelmezett cellakkal


        // referenciak masolasa a regi matrixbol
        
        for(int row = 0; row < minSize; row++)
            for(int col = 0; col < minSize; col++)
                newMrx.get(row).set(col, oldMrx.get(row).get(col)); // "new[row][col] = old[row][col]"

        
        // uj tagvaltozok beallitasa
        
        size = newSize;
        cellMrx = newMrx;
    }



    /**
     * 
     * @param manipulator Minden cellara meghivja az apply fuggvenyet
     */
    public synchronized void forEach(GridManipulator manipulator)
    {
        // uj allapotok kiszamitasa
        for(int row = 0; row < size; row++)
        {
            for(int col = 0; col < size; col++)
            {
                Vector2 pos = new Vector2(col, row);
                manipulator.apply(this, pos);
            }
        }
    }


    /**
     * Megmondja a Vector2-rol, hogy
     * ilyen pozicioban van-e (lehet-e) cella
     * 
     * @param p A kerdeses pozicio
     * @return Ervenyes-e a pozicio
     */
    public boolean isValid(Vector2 p)
    {
        return p.x() >= 0 && p.x() < size && p.y() >= 0 && p.y() < size;
    }

}
