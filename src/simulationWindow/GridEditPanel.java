package simulationWindow;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.*;

import simulation.Clearer;
import simulation.Randomizer;

public class GridEditPanel extends JPanel
{
    private SimulationWindow parent;

    private JButton resetButton;
    private JButton randomizeButton;
    private JLabel gridSizeLabel;
    private JSpinner gridSizeSpinner;

    private Clearer clearer;
    private Randomizer randomizer;

    /**
     * Letrehozza a tabla modosito panelt.
     * A panelnek ismernie kell a tartalmazo SimulationWindow-t.
     * 
     * @param parent Tartalmazo ablak
     */
    public GridEditPanel(SimulationWindow parent)
    {
        this.parent = parent;

        randomizer = new Randomizer();
        clearer = new Clearer();



        setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;


        // "palya meret" label
        gridSizeLabel = new JLabel("Grid size: ");
        c.gridx = 0;
        c.gridy = 0;
        add(gridSizeLabel, c);


        // palyameret allito szam-input
        gridSizeSpinner = new JSpinner();
        gridSizeSpinner.setModel(new SpinnerNumberModel(10, 10, 150, 10));
        gridSizeSpinner.addChangeListener
            ( (e) -> parent.resize((int)gridSizeSpinner.getValue()) ); // beallitja a kijelzot
        c.gridx = 1;
        c.gridy = 0;
        add(gridSizeSpinner, c);


        // palya visszaallito gomb
        resetButton = new JButton("reset grid");
        resetButton.addActionListener
            ( (e) -> parent.forEach(clearer) ); // letorli a palyat
        c.gridx = 0;
        c.gridy = 1;
        add(resetButton, c);


        // randomizalo gomb
        randomizeButton = new JButton("randomize");
        randomizeButton.addActionListener
            ( (e) -> parent.forEach(randomizer) ); // randomizalja a palyat
        c.gridx = 1;
        c.gridy = 1;
        add(randomizeButton, c);

    }

    /**
     * Ha valamiert megvaltozik a palya/szimulacio allapota,
     * akkor a UI-t frissiteni kell resetUI() hivasaval.
     * 
     * Frissiti a tablameret-csuszka allasat az aktualis meretre.
     */
    public void resetUI()
    {
        gridSizeSpinner.setValue(parent.getGridSize());
    }

    /**
     * Engedi/nem engedi a tabla modositasat
     * (Csak akkor szabad engedelyezni, ha a szimulacio meg van allitva)
     * 
     * @param b Lehessen-e hasznalni a panelt
     */
    public void enableControls(boolean b)
    {
        resetButton.setEnabled(b);
        randomizeButton.setEnabled(b);
        gridSizeLabel.setEnabled(b);
        gridSizeSpinner.setEnabled(b);
    }


}
