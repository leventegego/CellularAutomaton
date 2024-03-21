package simulation;

import java.io.*;

public class Cell implements Serializable
{

    boolean alive;
    boolean aliveNext;

    /**
     * Letrehoz egy uj cellat.
     * A kezdoallapotban a cella "halott".
     */
    public Cell()
    {
        alive = false;
        aliveNext = false;
    }

    /**
     * A cellat a kovetkezo allapotba viszi,
     * amit a setNext() allitott be.
     */
    public void update()    // kor vegen beall a kovetkezo ertekre
    {
        alive = aliveNext;
        aliveNext = false;
    }

    /**
     * Cella aktualis allapota.
     * 
     * @return true: el, false: halott
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * A cella beallitott allapota a kovetkezo iteracioban.
     * 
     * @return true: elo lesz, false: halott lesz
     */
    public boolean isAliveNext()
    {
        return aliveNext;
    }

    /**
     * Cella aktualis allapotat allitja.
     * 
     * @param b true: elo, false: halott
     */
    public void setAlive(boolean b)
    {
        alive = b;
    }

    /**
     * Cella kovetkezo iteracio-beli allapotat allitja.
     * 
     * @param b true: elo, false: halott
     */
    public void setNext(boolean b)
    {
        aliveNext = b;
    }
}
