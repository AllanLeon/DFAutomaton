/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton;

import dfautomaton.UI.DrawingState;
import dfautomaton.model.Transition;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class TransitionSymbolDialog extends JDialog {
    
    private JPanel contentPane;
    private JTextField textBox;
    private JButton addBtn;
    private JButton okBtn;
    private Transition transition;
    
    public TransitionSymbolDialog(JFrame parent, Transition transition) {
        super(parent);
        this.transition = transition;
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
                if (textBox.getText().length() == 0) {
                    transition.addSymbol('\u03B5');
                } else {
                    transition.addSymbol(textBox.getText().charAt(0));
                    textBox.setText("");
                }
            }
        });
        
        addBtn = new JButton();
        addBtn.setText("+");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textBox.getText().length() == 0) {
                    transition.addSymbol('\u03B5');
                } else {
                    transition.addSymbol(textBox.getText().charAt(0));
                    textBox.setText("");
                }
            }
        });
        
        okBtn = new JButton();
        okBtn.setText("OK");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (transition.getSymbols().size() > 0) {
                    UI.drawingState = DrawingState.Drawing;
                    setVisible(false);
                    dispose();
                }
            }
        });
        
        textBox.setBounds(50, 30, 120, 30);
        addBtn.setBounds(200, 30, 50, 30);
        okBtn.setBounds(260, 30, 60, 30);
        
        contentPane.add(textBox);
        contentPane.add(addBtn);
        contentPane.add(okBtn);
    }
}
