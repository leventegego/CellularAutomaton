package simulationWindow;

import java.awt.BorderLayout;

import javax.swing.*;

public class RuleInputPanel extends JPanel
{

    private SimulationWindow parent;
    
    private JTextField ruleInput;
    private JButton setRuleButton;

    /**
     * Letrehozza a szabaly-input panelt.
     * A panelnek ismernie kell a tartalmazo SimulationWindow-t.
     * 
     * @param parent Tartalmazo ablak
     */
    public RuleInputPanel(SimulationWindow parent)
    {
        this.parent = parent;

        setLayout(new BorderLayout());


        // szabaly beallito szoveg mezo
        ruleInput = new JTextField(10);
        add(ruleInput, BorderLayout.LINE_START);
        

        // szabaly beallito gomb
        setRuleButton = new JButton("set rule");
        setRuleButton.addActionListener
            ( (e) -> parent.setRules(ruleInput.getText()) ); // beallitja a szabalyokat
        add(setRuleButton, BorderLayout.LINE_END);
    }

    /**
     * Ha valamiert megvaltozik a palya/szimulacio allapota,
     * akkor a UI-t frissiteni kell resetUI() hivasaval.
     * 
     * Frissiti a TextField-ben szereplo szoveget az aktualis szabalyra.
     */
    public void resetUI()
    {
        ruleInput.setText(parent.getRules());
    }

    /**
     * Engedi/nem engedi a tabla modositasat
     * (Csak akkor szabad engedelyezni, ha a szimulacio meg van allitva)
     * 
     * @param b Lehessen-e hasznalni a panelt
     */
    public void enableControls(boolean b)
    {
        ruleInput.setEnabled(b);
        setRuleButton.setEnabled(b);
    }


}