package simulation;

import java.util.*;

public class Simulator implements Runnable
{
    private Grid grid;
    private List<GridManipulator> manipulators;
    private GridDisplay display;

    private volatile int msPerTick;

    private volatile boolean pauseFlag;
    private volatile boolean stopFlag;
    private Object monitor;     // itt varakozik a futtato szal, ha meg van allitva

    /**
     * Letrehoz egy szimulaciot, ami egy tablat
     * adott idokozonkent 'leptet' es az megjeleniti
     * egy Display interface-en.
     * 
     * A szimulacio letrehozaskor meg nem indul el.
     * Erdemes Thread-be rakni.
     * 
     * @param grid A tabla, amit szimulalni kell.
     * @param manipulator A valtoztatas, amit minden iteracioban el kell vegezni a tablan.
     * @param display Itt jelenik meg a tabla.
     * @param msPerTick Ennyi ido telik el az iteraciok kozott. (ms)
     */
    public Simulator(Grid grid, List<GridManipulator> manipulator, GridDisplay display, int msPerTick)
    {
        this.grid = grid;
        this.manipulators = manipulator;
        this.display = display;
        
        this.msPerTick = msPerTick;

        pauseFlag = false;
        stopFlag = false;
        monitor = new Object();
    }

    /**
     * 
     * @return Meg van-e allitva a szimulacio
     */
    public boolean isPaused()
    {
        return pauseFlag;
    }

    /**
     * Megallitja/elinditja a szimulaciot.
     * @param p Meg legyen-e allitva
     */
    public void setPaused(boolean p)
    {
        pauseFlag = p;
        
        if(!p)  // ujra kell inditani
        {
            synchronized(monitor)
            {
                monitor.notifyAll();    // varakozo szalak felebresztese
            }
        }
    }

    /**
     * Befejezi a szimulaciot.
     * Ez utan nem lehet ujra inditani.
     */
    public void stopSimulation()
    {
        stopFlag = true;

        synchronized(monitor)
        {
            monitor.notifyAll();    // ha meg lenne allitva
        }
    }

    /**
     * Egy lepest hajt vegre a szimulacioban.
     */
    public void step()
    {
        for(GridManipulator m : manipulators)
            grid.forEach(m);
    }

    
    /**
     * Kezdetben elinditja a szimulaciot.
     * Thread-en keresztul is hivhato.
     */
    public void run()
    {
        try
        {
            while(true)
            {
                
                if(pauseFlag)
                {
                    synchronized(monitor)
                    {
                        monitor.wait(); // itt var, ameddig fel nem ebresztik
                    }
                }

                if(stopFlag)
                    break;

                step();

                display.display(grid);

                Thread.sleep(msPerTick);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }



    // ---------- getterek ----------

    public int getMsPerTick()
    {
        return msPerTick;
    }



    // ---------- setterek ----------

    public void setGrid(Grid grid)
    {
        this.grid = grid;
    }

    public void setManipulators(List<GridManipulator> manipulators)
    {
        this.manipulators = manipulators;
    }

    public void setDisplay(GridDisplay display)
    {
        this.display = display;
    }

    public void setMsPerTick(int msPerTick)
    {
        this.msPerTick = msPerTick;
    }
}
