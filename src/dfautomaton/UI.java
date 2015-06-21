package dfautomaton;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

/**
 * A JFrame containing all elements
 *
 * @author Franco Montiel
 */
public class UI extends JFrame implements ActionListener {

    private JPanel panel;
    private JButton newState;
    private JButton newFState;
    private JButton newTransition;
    private MouseHandler mouseHandler;
    private DrawableState ds;
    public Graphics g;

    public static int WINDOW_WIDTH = 800;
    public static int WINDOW_HEIGHT = 600;
    public static int PANEL_WIDTH = 770;
    public static int PANEL_HEIGHT = 520;

    /**
     * Create the frame.
     */
    public UI() {
        initialize();
        setVisible(true);
    }

    /**
     * Initialize and set up the basic components of the frame.
     */
    private void initialize() {
        setTitle("DFA");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setBounds(10, 10, PANEL_WIDTH, PANEL_HEIGHT);
        mouseHandler = new MouseHandler();
        panel.addMouseListener(mouseHandler);
        panel.addMouseMotionListener(mouseHandler);

        newState = new JButton("New State");
        setMaterialLNF(newState);
        newState.setFocusable(false);
        newState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                drawInitState();
            }
        });

        newTransition = new JButton("New Transition");
        setMaterialLNF(newTransition);
        newTransition.setFocusable(false);

        newFState = new JButton("New Final State");
        setMaterialLNF(newFState);
        newFState.setFocusable(false);

        newState.setBounds(WINDOW_WIDTH / 4 - 150, WINDOW_HEIGHT - 60, 100, 25);
        newTransition.setBounds(WINDOW_WIDTH / 4 - 40, WINDOW_HEIGHT - 60, 130, 25);
        newFState.setBounds(WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT - 60, 130, 25);

        getContentPane().add(panel);
        getContentPane().add(newState);
        getContentPane().add(newTransition);
        getContentPane().add(newFState);
    }

    public void setMaterialLNF(Component comp) {
        comp.setBackground(Color.BLACK);
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comp.setForeground(Color.WHITE);
    }

    public void drawInitState() {
        ds = new DrawableState(150, 150, 50);
        mouseHandler.getCreatorStates().add(ds);
        g = getGraphics();
        g.setColor(Color.WHITE);
        g.drawOval(ds.getPosx(), PANEL_HEIGHT - ds.getPosy(),
                ds.getRadius(), ds.getRadius());
    }

    public void drawState(DrawableState d) {
        g = getGraphics();
        g.setColor(Color.WHITE);
        g.drawOval(d.getPosx(), PANEL_HEIGHT - d.getPosy(),
                d.getRadius(), d.getRadius());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        paint();
    }

    private void paint() {
        g = getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        for(DrawableState d : mouseHandler.getCreatorStates()) {
            drawState(d);
        }
    }
}
