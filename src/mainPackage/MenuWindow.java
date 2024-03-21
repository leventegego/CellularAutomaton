package mainPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;
import java.awt.Dimension;
import java.awt.Font;

import simulationWindow.SimulationWindow;

public class MenuWindow extends JFrame implements ActionListener
{

    private JFileChooser fc;
    private JLabel titleLabel;
    private JButton openFileButton;
    private JButton defaultButton;

    /**
     * Letrehoz egy menu ablakot.
     * Az ablak bezarasa magaban nem allitja le a programot,
     * csak ha minden mas ablak is bezart.
     */
    public MenuWindow()
    {
        super("Main menu");

        setSize(280, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);
        

        fc = new JFileChooser();


        titleLabel = new JLabel("Cellular automata");
        titleLabel.setFont(new Font("Comic Sans", Font.BOLD, 20));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 70)));
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 100)));

        
        openFileButton = new JButton("open file");
        openFileButton.addActionListener(this);
        openFileButton.setAlignmentX(CENTER_ALIGNMENT);
        add(openFileButton);

        

        defaultButton = new JButton("open default");
        defaultButton.addActionListener(this);
        defaultButton.setAlignmentX(CENTER_ALIGNMENT);
        add(defaultButton);


        setVisible(true);
        
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == openFileButton)
        {
            fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fc.showOpenDialog(openFileButton);
            new SimulationWindow(fc.getSelectedFile().getAbsolutePath());
        }
        else if(e.getSource() == defaultButton)
        {
            new SimulationWindow();
        }
    }
    
}
