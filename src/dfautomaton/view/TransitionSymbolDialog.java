package dfautomaton.view;

import dfautomaton.data.Constants;
import dfautomaton.model.Transition;
import dfautomaton.model.TransitionInfo;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Allan Leon
 */
public class TransitionSymbolDialog extends JDialog {
    
    private JPanel contentPane;
    private JTextField symbolBox;
    private JTextField topBox;
    private JTextField nextTopBox;
    private JButton dzetaBtn;
    private JButton epsilonBtn;
    private JButton addBtn;
    private JButton okBtn;
    private JButton xBtn;
    private JLabel commaLbl;
    private JLabel pipeLbl;
    private JLabel options;
    private Transition transition;
    
    public TransitionSymbolDialog(JFrame parent, Transition transition) {
        super(parent);
        this.transition = transition;
        initializeComponents();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack(); 
        setModal(true);
        setVisible(true);
    }
    
    private void initializeComponents() {
        setTitle("Transition Input");
        setPreferredSize(new Dimension(350, 130));
        setResizable(false);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        symbolBox = new JTextField();
        symbolBox.setDocument(new JTextFieldLimit(1));
        symbolBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addTransitionInfo();
            }
        });
        
        topBox = new JTextField();
        topBox.setDocument(new JTextFieldLimit(1));
        topBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addTransitionInfo();
            }
        });
        
        nextTopBox = new JTextField();
        nextTopBox.setDocument(new JTextFieldLimit(2));
        nextTopBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addTransitionInfo();
            }
        });
        
        dzetaBtn = new JButton();
        dzetaBtn.setText(Constants.DZETA + "");
        dzetaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyToClipboard(Constants.DZETA + "");
            }
        });
        
        epsilonBtn = new JButton();
        epsilonBtn.setText(Constants.EPSILON + "");
        epsilonBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyToClipboard(Constants.EPSILON + "");
            }
        });
        
        addBtn = new JButton();
        addBtn.setText("+");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTransitionInfo();
            }
        });
        
        okBtn = new JButton();
        okBtn.setText("OK");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (transition.getOptions().size() > 0) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Transition doesn't contain any option!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        xBtn = new JButton();
        xBtn.setText("X");
        xBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTransition();
            }
        });
        
        options = new JLabel(transition.getTransitionText());
        commaLbl = new JLabel(",");
        pipeLbl = new JLabel("|");
        
        symbolBox.setBounds(70, 30, 30, 30);
        commaLbl.setBounds(100, 30, 10, 30);
        topBox.setBounds(110, 30, 30, 30);
        pipeLbl.setBounds(140, 30, 10, 30);
        nextTopBox.setBounds(150, 30, 30, 30);
        dzetaBtn.setBounds(200, 0, 50, 30);
        epsilonBtn.setBounds(250, 0, 50, 30);
        addBtn.setBounds(200, 30, 50, 30);
        okBtn.setBounds(260, 30, 60, 30);
        xBtn.setBounds(10, 30, 50, 30);
        options.setBounds(10, 80, 350, 20);
        
        contentPane.add(symbolBox);
        contentPane.add(commaLbl);
        contentPane.add(topBox);
        contentPane.add(pipeLbl);
        contentPane.add(nextTopBox);
        contentPane.add(dzetaBtn);
        contentPane.add(epsilonBtn);
        contentPane.add(addBtn);
        contentPane.add(okBtn);
        contentPane.add(xBtn);
        contentPane.add(options);
    }
    
    private void addTransitionInfo() {
        if (topBox.getText().length() > 0 && nextTopBox.getText().length() > 0) {
            if (symbolBox.getText().length() == 0) {
                transition.addOption(new TransitionInfo(Constants.EPSILON, topBox.getText().charAt(0), nextTopBox.getText()));
            } else {
                transition.addOption(new TransitionInfo(symbolBox.getText().charAt(0), topBox.getText().charAt(0), nextTopBox.getText()));
            }
            symbolBox.setText("");
            topBox.setText("");
            nextTopBox.setText("");
            options.setText(transition.getTransitionText());
        }
    }
    
    private void removeTransition() {
        MainFrame.getAutomaton().getTransitions().remove(transition);
        dispose();
    }
    
    private void copyToClipboard(String text) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(text);
        clipboard.setContents(strSel, null);
        JOptionPane.showMessageDialog(null, text + " copied to clipboard!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    class JTextFieldLimit extends PlainDocument {
        
        private int limit;
        
        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        JTextFieldLimit(int limit, boolean upper) {
            super();
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }
}
