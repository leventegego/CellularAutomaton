package simulationWindow;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.*;


public class SimulationPanel extends JPanel
{
    private SimulationWindow parent;
    
    private JButton pauseButton;
    private JButton stepButton;
    private JLabel speedLabel;
    private JSlider speedSlider;

    /**
     * Letrehozza a szimulacio-kezelo panelt.
     * A panelnek ismernie kell a tartalmazo SimulationWindow-t.
     * 
     * @param parent Tartalmazo ablak
     */
    public SimulationPanel (SimulationWindow parent)
    {
        this.parent = parent;
        
        // szimulacio vezerlo panel
        setLayout(new GridBagLayout());
        

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;


        // szunet gomb
        pauseButton = new JButton();
        resetUI();
        pauseButton.addActionListener
            ( (e) -> parent.setPaused( ! parent.isPaused() ) );
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        add(pauseButton, c);


        // egye lepes vegrehajtasa gomb
        stepButton = new JButton("step");
        stepButton.addActionListener
            ( (e) -> parent.stepSimulation() );
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        add(stepButton, c);


        // "sebesseg" label
        speedLabel = new JLabel("speed: ");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        add(speedLabel, c);


        // sebesseg szablyzo gomb
        speedSlider = new JSlider();
        speedSlider.setOrientation(JSlider.HORIZONTAL);
        speedSlider.setMinimum(1);
        speedSlider.setMaximum(50);
        speedSlider.setValue(1000 / parent.getMsPerTick());
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.addChangeListener
            ( (e) -> parent.setMsPerTick(1000 / speedSlider.getValue()) );
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        add(speedSlider, c);
    }

    /**
     * Ha valamiert megvaltozik a palya/szimulacio allapota,
     * akkor a UI-t frissiteni kell resetUI() hivasaval.
     * 
     * Beallitja a megallito gomb szoveget.
     */
    public void resetUI()
    {
        if(parent.isPaused())
            pauseButton.setText("start");
        else
            pauseButton.setText("stop");
    }

    /**
     * Engedi/nem engedi a tabla modositasat
     * (Csak akkor szabad engedelyezni, ha a szimulacio meg van allitva)
     * 
     * A megallito gombot es a sebesseg allito csuszkat mindig lehet hasznalni.
     * 
     * @param b Lehessen-e hasznalni a panelt
     */
    public void enableControls(boolean b)
    {
        // pauseButton.setEnabled(b);   a megallito/indito gomb es a sebesseg slider nem kapcsol ki
        stepButton.setEnabled(b);
        // speedSlider.setEnabled(b);
    }

    
}
