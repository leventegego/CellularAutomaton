package simulationWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;

import javax.swing.*;

import simulation.*;

import java.io.*;
import java.util.*;

public class SimulationWindow extends JFrame
{

    private Grid grid;
    private Ruleset rules;
    private DisplayPanel display;
    private Simulator simulator;


    private JPanel controlPanel;

    private SimulationPanel simulationPanel;
    private RuleInputPanel ruleInputPanel;
    private GridEditPanel gridEditPanel;
    private FilePanel filePanel;
    
    /**
     * Letrehozza es megjeleniti a szimulacios ablakot, az alapertelmezett pozicioval.
     * Az ablak bezarasa onmagaban nem jelenti a program leallasat.
     */
    public SimulationWindow()
    {
        super("Cellular Automaton");

        init();
        defaultPosition();
        new Thread(simulator).start();
    }

    /**
     * Letrehozza a es megjeleniti szimulacios ablakot.
     * A megadott fajlbol betolti a mentett allast.
     * 
     * Ha a file hibas, akkor az alapertelmezett poziciot tolti be.
     * 
     * @param fileName Utvonal a betoltendo filehoz
     */
    public SimulationWindow(String fileName)
    {
        super("Cellular Automaton");

        init();
        load(fileName);
        new Thread(simulator).start();
    }

    /**
     * Inicializalja az ablak komponenseit es a szimulaciot.
     */
    private void init()
    {
        // ---------- szimulacio ----------
        
        simulator = new Simulator(grid, getManipulators(), display, 50);
        
        

        // ---------- ablak komponensei ----------
        
        // ablak
        setSize(850, 600);
        setMinimumSize(new Dimension(600, 450));
        addWindowListener(new WindowCloseListener());
        setLayout(new BorderLayout());

        
        // megjelenito panel
        display = new DisplayPanel(this);
        add(display, BorderLayout.WEST);


        // vezerlo panel
        controlPanel = new JPanel();
        controlPanel.setBackground(Color.LIGHT_GRAY);
        controlPanel.setMinimumSize(new Dimension(200, 300));
        add(controlPanel, BorderLayout.CENTER);


        // panelek
        simulationPanel = new SimulationPanel(this);
        controlPanel.add(simulationPanel);

        ruleInputPanel = new RuleInputPanel(this);
        controlPanel.add(ruleInputPanel);
        
        gridEditPanel = new GridEditPanel(this);
        controlPanel.add(gridEditPanel);

        filePanel = new FilePanel(this);
        controlPanel.add(filePanel);

        setVisible(true);
    }


    class WindowCloseListener extends WindowAdapter
    {
        /**
         * Az ablak bezarasat kezeli.
         * Leallitja a szimulaciot es megsemmisiti az ablakot.
         * 
         * @param e Az esemennyel kapcsolatos informaciok.
         */
        public void windowClosing(WindowEvent e)
        {
            simulator.stopSimulation();
            dispose();
        }
    }



    /**
     * Beallitja az alapertelmezett allast.
     * Convay-fele eletjatek, azaz B3/S23
     * 50x50 -es ures palya.
     */
    private void defaultPosition()
    {
        rules = new Ruleset("B3/S23");
        grid = new Grid(50);

        reset();
    }

    /**
     * Letrehozza a szimulacio futtatasahoz szukseges
     * manipulator listat a rules tagvaltozo alapjan.
     * 
     * @return Manipulatorok listaja a Simulatorhoz
     */
    private List<GridManipulator> getManipulators()
    {
        List<GridManipulator> m = new ArrayList<GridManipulator> ();
        m.add(rules);
        m.add(new Updater());
        return m;
    }


    /**
     * Ha a tabla vagy a szabalyrendszer megvaltozott akkor
     * a szimulacio parametereit es a UI megjelenitett ertekeit
     * frissiteni kell a reset() hivasaval.
     * Ehhez a reset() meg is alllitja a szimulaciot, ha kell.
     */
    private void reset()
    {
        setPaused(true);
        
        simulator.setGrid(grid);
        simulator.setManipulators(getManipulators());
        simulator.setDisplay(display);

        display.display(grid);

        // input mezok beallitasa a jelenlegi ertekekre
        simulationPanel.resetUI();
        gridEditPanel.resetUI();
        ruleInputPanel.resetUI();
        // filePanel.resetUI(); // nem kell
    }

    /**
     * Megallitja/elinditja a szimulaciot es a beallitja,
     * hogy melyik komponensek hasznalhatoak.
     * 
     * @param p Alljon-e a szimulacio?
     */    
    public void setPaused(boolean p)
    {
        enableControls(p);
        simulator.setPaused(p);
        simulationPanel.resetUI();
    }

    /**
     * 
     * @return Meg van-e allitva a szimulacio
     */
    public boolean isPaused()
    {
        return simulator.isPaused();
    }

    /**
     * Visszaadja, hogy hany ms telik el ket iteracio kozt.
     * 
     * @return A szimulacio msPerTick parametere
     */
    public int getMsPerTick()
    {
        return simulator.getMsPerTick();
    }

    /**
     * Egyetlen lepest hajt vegre
     */
    public void stepSimulation()
    {
        simulator.step();
        display.display(grid);
    }

    /**
     * Beallitja a szimulacio periodusidejet
     * 
     * @param msPerTick Hany ms teljen el ket iteracio kozt?
     */
    public void setMsPerTick(int msPerTick)
    {
        simulator.setMsPerTick(msPerTick);
    }

    /**
     * 
     * @return Aktualis szabalyrendszert reprezentalo string
     */
    public String getRules()
    {
        return rules.toString();
    }

    public void setRules(String ruleString)
    {
        try
        {
            rules.setRule(ruleString);
        }
        catch(IllegalArgumentException e)
        {
            JOptionPane.showMessageDialog(
                this, "Helytelen szabaly!", "Hibas input",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Engedi/nem engedi a paneleket
     * (Csak akkor szabad engedelyezni, ha a szimulacio meg van allitva)
     * 
     * @param b Lehessen-e hasznalni a paneleket
     */
    private void enableControls(boolean b)
    {
        simulationPanel.enableControls(b);
        ruleInputPanel.enableControls(b);
        gridEditPanel.enableControls(b);
        filePanel.enableControls(b);
    }

    /**
     * 
     * @return Aktualis tabla merete
     */
    public int getGridSize()
    {
        return grid.getSize();
    }

    /**
     * Visszaadja a cella allapotat
     *  
     * @param gridPos Cella kooridnatai
     * @return El-e a cella
     */
    public boolean getAlive(Vector2 gridPos)
    {
        return grid.at(gridPos).isAlive();
    }

    /**
     * Beallitja egy cella allapotat
     * 
     * @param gridPos cella koordinatai
     * @param alive cella allapota
     */
    public void setAlive(Vector2 gridPos, boolean alive)
    {
        if(! grid.isValid(gridPos))  // hibas pozicio eseten semmi
            return;

            
        synchronized(grid)   // a szimulacioval parhuzamosan modosithat
        {
            grid.at(gridPos).setAlive(alive);;
        }
        
        display.display(grid);
    }

    /**
     * 
     * @param manipulator Minden cellara meghivja az apply fuggvenyet
     */
    public void forEach(GridManipulator manipulator)
    {
        grid.forEach(manipulator);
        display.display(grid);
    }

    public void resize(int newSize)
    {
        grid.resize(newSize);
        display.display(grid);
    }

    /**
     * Elmenti az allast a megadott helyre.
     * A tabla es a szabalyrendszer objektumait menti
     * java serializacioval.
     * 
     * Ha hiba tortent, akkor felugro dialogussal jelzi.
     * 
     * @param fileName Utvonal a cel filehoz
     */
    public void save(String fileName)
    {
        try
        {
            File file = new File(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

            oos.writeObject(grid);
            oos.writeObject(rules);
            
            oos.close();

            JOptionPane.showMessageDialog(
                this, "Elmentve ide: " + file.getAbsolutePath(), "Sikeres mentes",
                JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(
                this, "Nem sikerult elmenteni!", "Sikertelen mentes",
                JOptionPane.ERROR_MESSAGE);
            
        }
    }

    /**
     * Betolti az allast a megadott helyrol.
     * Ha hiba tortent, akkor nem modosul az allas.
     * Ha sikeresen betoltott, akkor a UI ertekeit is frissiti
     * a beolvasott ertekekre.
     * 
     * @param fileName Utvonal a betoltendo filehoz
     */
    public void load(String fileName)
    {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(new File(fileName)));
            
            Grid newGrid = (Grid)ois.readObject();
            Ruleset newRuleset = (Ruleset)ois.readObject();

            // ha hibas, akkor itt mar volt kivetel, es a tagvaltozok nem lesznek atallitva
            
            grid = newGrid;
            rules = newRuleset;
            reset();
            
            ois.close();

        }
        catch(Exception e)
        {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                this, "Nem lehet betolteni a fajlt!", "Hibas fajl",
                JOptionPane.ERROR_MESSAGE);

        }
    }
    
    
}
