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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Allan Leon
 */
public class InputDialog extends JDialog {
    
    private JPanel contentPane;
    private JTextField textBox;
    private JButton okBtn;
    private Automaton automaton;
    
    public InputDialog(JFrame parent, Automaton automaton) {
        super(parent);
        this.automaton = automaton;
        initializeComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack(); 
        setModal(true);
        setVisible(true);
    }
    
    private void initializeComponents() {
        setTitle("");
        setPreferredSize(new Dimension(350, 100));
        setResizable(false);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        textBox = new JTextField();
        textBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                new ResultsFrame(automaton, textBox.getText());
            }
        });
        
        okBtn = new JButton();
        okBtn.setText("OK");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ResultsFrame(automaton, textBox.getText());
            }
        });
        
        textBox.setBounds(50, 30, 150, 30);
        okBtn.setBounds(260, 30, 60, 30);
        
        contentPane.add(textBox);
        contentPane.add(okBtn);
    }    
}
