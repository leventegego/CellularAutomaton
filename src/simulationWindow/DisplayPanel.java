package simulationWindow;

import javax.swing.*;

import simulation.*;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class DisplayPanel extends JPanel implements GridDisplay, MouseListener, MouseMotionListener
{

    SimulationWindow parent;
    BufferedImage img;

    /**
     * Letrehozza a megjelenitopanelt
     * A panelnek ismernie kell a tartalmazo SimulationWindow-t
     * 
     * @param parent Tartalmazo ablak.
     */
    public DisplayPanel(SimulationWindow parent)
    {
        this.parent = parent;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Mindig a leheto legnagyobb negyzetet adja vissza,
     * mivel a tabla mindig negyzet alaku.
     */
    public Dimension getPreferredSize()
    {
        Dimension s = getParent().getSize();
        int min = (int)Math.min(s.getWidth(), s.getHeight());
        return new Dimension(min, min);
    }
    
    // megvaltozott pozicio eseten kell meghivni

    /**
     * GridDisplay-t implementalja
     * Ujrageneralja a palya megjelenitesehez szukseges BufferedImage-et
     * Megvaltozott palya eseten meg kell hivni
     * 
     * @param grid A palya, amit meg kell jeleniteni
     */
    public void display(Grid grid)
    {
        generateImage(grid);
        drawImage(getGraphics());
    }

    /**
     * Az img tagvaltozoba letrehoz egy kepet a Palyarol.
     * A kepen egy cella egy pixelnek felel meg.
     * 
     * @param grid A palya, amit meg kell rajzolni
     */
    private void generateImage(Grid grid)
    {
        img = new BufferedImage(grid.getSize(), grid.getSize(), BufferedImage.TYPE_INT_ARGB);

        for(int row = 0; row < grid.getSize(); row++)
            for(int col = 0; col < grid.getSize(); col++)
                if(grid.at(new Vector2(col, row)).isAlive())
                    img.setRGB(col, row, 0xffffffff);
    }

    /**
     * A generalt kepet a megfelelo meretre nagyitja, es kirajzolja.
     * 
     * @param g A Graphics objektum, ahova a kep kerul (Sajat Graphics peldany)
     */
    private void drawImage(Graphics g)
    {
        g.drawImage(img, 0, 0, (int)getSize().getWidth(), (int)getSize().getHeight(), Color.DARK_GRAY, null);
    }

    // ha a megjelenites modja valtozott, ujra kell rajzolni
    // a kep maga nem valtozik.

    /**
     * Ha a komponens merete megvaltozott, akkor ujra kell rajzolni
     * De nem kell urja generalni a kepet.
     * 
     * @param g A Graphics objektum, ahova a kep kerul (Sajat Graphics peldany)
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawImage(g);
    }

    // kepernyo-pozicio -> cella koordinata

    /**
     * Lokalis koordinatat (pixelekben) atvaltja tabla koordinataba.
     * (Kepernyo pozicio melyik cellanak felel meg.)
     * 
     * @param x Lokalis x koordinata
     * @param y Lokalis y koordinata
     * @return Cella koordinatai (a tabla szerint)
     */
    Vector2 pointToCell(int x, int y)
    {
        return new Vector2
        (
            x * parent.getGridSize() / getSize().width,
            y * parent.getGridSize() / getSize().height
        );
    }



    // ---------- eger eventek ----------

    public void mousePressed(MouseEvent e)
    {

    }

    public void mouseEntered(MouseEvent e)
    {

    }

    public void mouseReleased(MouseEvent e)
    {

    }

    /**
     * Akkor hivodik, amikor az egergombot lenyomva huzzuk a kurzort a komponensen.
     * Elore allitja annak a cellanak az allapotat, amelyikre huzzuk a kurzort.
     * 
     * @param e Informaciok az eger helyzeterol, gombrol stb
     */
    public void mouseDragged(MouseEvent e)
    {
        Vector2 gridPos = pointToCell(e.getX(), e.getY());
        parent.setAlive(gridPos, true);
    }

    /**
     * Akkor hivodik, amikor rakattintunk a komponensre.
     * 'Atkapcsolja' a cellanak az allapotat, amire kattintunk.
     * 
     * @param e Informaciok az eger helyzeterol, gombrol stb
     */
    public void mouseClicked(MouseEvent e)
    {
        Vector2 gridPos = pointToCell(e.getX(), e.getY());
        parent.setAlive(gridPos, !parent.getAlive(gridPos));
    }

    public void mouseMoved(MouseEvent e)
    {

    }

    public void mouseExited(MouseEvent e)
    {

    }

}
