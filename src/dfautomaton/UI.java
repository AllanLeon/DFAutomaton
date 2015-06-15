package dfautomaton;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

/**
 * A JFrame containing all elements
 * @author Franco Montiel
 */
public class UI extends JFrame {

    private JPanel panel;
    private JButton newState;
    private JButton newFState;
    private JButton newTransition;
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;

    /**
     * Create the frame.
     */
    public UI() {
        initialize();
    }

    /**
     * Initialize and set up the basic components of the frame.
     */
    private void initialize() {
        setTitle("DFA");
        this.getContentPane().setBackground(Color.BLACK);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setBounds(10, 10, WINDOW_WIDTH - 30, WINDOW_HEIGHT - 80);

        newState = new JButton("New State");
        setMaterialLNF(newState);
        newTransition = new JButton("New Transition");
        setMaterialLNF(newTransition);
        newFState = new JButton("New Final State");
        setMaterialLNF(newFState);
        
        newState.setBounds(WINDOW_WIDTH / 4 - 150, WINDOW_HEIGHT - 60, 100, 25);
        newTransition.setBounds(WINDOW_WIDTH / 4 - 40, WINDOW_HEIGHT - 60, 130, 25);
        newFState.setBounds(WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT - 60, 130, 25);
        
        getContentPane().add(panel);
        getContentPane().add(newState);
        getContentPane().add(newTransition);
        getContentPane().add(newFState);

        super.paintComponents(getGraphics());
        paint();
    }

    private void paint() {

    }

    @Override
    public void update(Graphics g) {
    }

    @Override
    public void paint(Graphics g) {
    }
    
    public void setMaterialLNF(Component comp) {
        comp.setBackground(Color.BLACK);
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comp.setForeground(Color.WHITE);
    }
}