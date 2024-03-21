package simulationWindow;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.*;

public class FilePanel extends JPanel
{
    // private SimulationWindow parent;

    private JTextField fileNameField;
    private JButton saveButton;
    private JButton loadButton;

    /**
     * Letrehozza a file kezelo panelt.
     * A panelnek ismernie kell a tartalmazo SimulationWindow-t.
     * 
     * @param parent Tartalmazo ablak
     */
    public FilePanel(SimulationWindow parent)
    {
        // this.parent = parent;

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;


        // file nev input panel
        fileNameField = new JTextField(15);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        add(fileNameField, c);


        // mentes gomb
        saveButton = new JButton("save");
        saveButton.addActionListener
            ( (e) -> parent.save(fileNameField.getText()) ); // elmenti a poziciot
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.weightx = 1;
        add(saveButton, c);


        // betoltes gomb
        loadButton = new JButton("load");
        loadButton.addActionListener
            ( (e) -> parent.load(fileNameField.getText()) ); // betolti a poziciot
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.weightx = 1;
        add(loadButton, c);
    }

    /**
     * Engedi/nem engedi a fajlok kezeleset
     * (Csak akkor szabad engedelyezni, ha a szimulacio meg van allitva)
     * 
     * @param b Lehessen-e hasznalni a panelt
     */
    public void enableControls(boolean b)
    {
        fileNameField.setEnabled(b);
        saveButton.setEnabled(b);
        loadButton.setEnabled(b);
    }

}
