/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.view;

import dfautomaton.model.Automaton;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Allan Leon
 */
public class ReachableStatesDialog extends JDialog {
    
    private JPanel contentPane;
    private JLabel textLbl;
    private JButton yesBtn;
    private JButton noBtn;
    private Automaton automaton;
    
    public ReachableStatesDialog(JFrame parent, Automaton automaton) {
        super(parent);
        this.automaton = automaton;
        initializeComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack(); 
        setModal(true);
        setVisible(true);
    }
    
    private void initializeComponents() {
        setTitle("Existen estados inalcanzables");
        setPreferredSize(new Dimension(390, 120));
        setResizable(false);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        textLbl = new JLabel("¿Desea eliminar los estados que no pueden ser alcanzados?");
        
        yesBtn = new JButton();
        yesBtn.setText("OK");
        yesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                automaton.removeUnreachableStates();
                dispose();
            }
        });
        
        noBtn = new JButton();
        noBtn.setText("Cancelar");
        noBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        textLbl.setBounds(5, 5, 380, 30);
        yesBtn.setBounds(50, 40, 100, 30);
        noBtn.setBounds(200, 40, 100, 30);
        
        contentPane.add(textLbl);
        contentPane.add(yesBtn);
        contentPane.add(noBtn);
    }
}
